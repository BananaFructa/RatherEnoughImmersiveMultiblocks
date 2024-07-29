package BananaFructa.reim;

import blusunrize.immersiveengineering.api.MultiblockHandler;
import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.lib.manual.gui.GuiButtonManualNavigation;
import blusunrize.lib.manual.gui.GuiManual;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.Item;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class MultiblockStructureRecipeWrapper implements IRecipeWrapper {

    MultiblockHandler.IMultiblock multiblock;
    public ItemStack[] materialsRequired;
    public ItemStack multiblockItem;

    int triggerPos;

    static IBlockState notifyBlock = Blocks.CONCRETE.getStateFromMeta(5);

    public MultiblockStructureRecipeWrapper(MultiblockHandler.IMultiblock multiblock,ItemStack multiblockItem,int triggerPos) {
        this.multiblock = multiblock;
        materialsRequired = getMaterialsForStructure(multiblock.getStructureManual());
        this.multiblockItem = multiblockItem;
        this.triggerPos = triggerPos;
        init(0,0);
    }

    public static ItemStack[] getMaterialsForStructure(ItemStack[][][] structure) {

        HashMap<ItemkWithMeta,Integer> materials = new HashMap<>();
        for (int l = 0;l < structure[0].length;l++) {
            for (int w = 0; w < structure[0][0].length; w++) {
                for (int h = 0 ; h < structure.length; h++) {
                    ItemStack is = structure[h][l][w];
                    if (is == null || is.isEmpty()) continue;
                    Item b = is.getItem();
                    int meta = is.getMetadata();
                    int quantity = is.getCount();
                    ItemkWithMeta bm = new ItemkWithMeta(b,meta);
                    if (!materials.containsKey(bm)) {
                        materials.put(bm,quantity);
                    } else {
                        materials.put(bm,quantity + materials.get(bm));
                    }
                }
            }
        }

        ItemStack[] materialsArr = new ItemStack[materials.size()];

        int index = 0;
        for (ItemkWithMeta key : materials.keySet()) {
            Item b = key.b;
            int meta = key.meta;
            int quantity = materials.get(key);
            materialsArr[index++] = new ItemStack(b,quantity,meta);
        }

        return materialsArr;

    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInputs(ItemStack.class, Arrays.asList(materialsRequired));
        iIngredients.setOutputs(ItemStack.class, Collections.singletonList(multiblockItem));
    }

    public static class ItemkWithMeta {

        Item b;
        int meta;

        public ItemkWithMeta(Item b, int meta) {
            this.b = b;
            this.meta = meta;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ItemkWithMeta)) return false;
            ItemkWithMeta bm = ((ItemkWithMeta)obj);
            return bm.b == b && bm.meta == meta;
        }

        @Override
        public int hashCode() {
            return Objects.hash(b, meta);
        }
    }

    boolean canTick = true;
    boolean showCompleted = false;
    int tick = 0;

    float scale = 50f;
    float transX = 0;
    float transY = 0;
    float rotX=0;
    float rotY=0;
    List<String> componentTooltip;
    MultiblockRenderInfo renderInfo;
    MultiblockBlockAccess blockAccess;

    boolean shouldNotify = false;
    boolean showNotify = false;


    public void init(int x, int y) {
        int yOff = 0;
        if (multiblock.getStructureManual() != null) {
            this.renderInfo = new MultiblockRenderInfo(multiblock);
            this.blockAccess = new MultiblockBlockAccess(renderInfo);
            transX = x + 160/2 - Math.max(renderInfo.structureWidth,renderInfo.structureLength) / 2;
            transY = y + 120/2 - renderInfo.structureHeight/2;
            rotX = 25;
            rotY = -45;
            scale = multiblock.getManualScale();
            boolean canRenderFormed = multiblock.canRenderFormedStructure();

            yOff = (int) (transY + scale * Math.sqrt(renderInfo.structureHeight * renderInfo.structureHeight + renderInfo.structureWidth * renderInfo.structureWidth + renderInfo.structureLength * renderInfo.structureLength) / 2);
        }
    }

    int lastX = 0, lastY = 0;
    int clickX = 0, clickY = 0;

    boolean wasClicking = false;

    private boolean isInBox(int mx,int my,int x,int y,int w,int h) {
        return mx >= x && my >= y && mx < x + w && my < y +h;
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        if(isInBox(mouseX,mouseY,164,62,8,8)) {
            canTick = !canTick;
        }
        else if(isInBox(mouseX,mouseY,164,40,8,7)) {
            this.renderInfo.setShowLayer( Math.min(renderInfo.showLayer+1, renderInfo.structureHeight-1));
        }
        else if(isInBox(mouseX,mouseY,164,51,8,7)) {
            this.renderInfo.setShowLayer( Math.max(renderInfo.showLayer-1, -1));
        }
        else if(multiblock.canRenderFormedStructure() && isInBox(mouseX,mouseY,164,74,8,8)) showCompleted = !showCompleted;
        if (isInBox(mouseX,mouseY,164,107,8,8)) {
            shouldNotify = !shouldNotify;
        }
        return false;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        List<String> tooltip = new ArrayList<>();
        if (isInBox(mouseX,mouseY,164,40,8,7)) {
            tooltip.add("Layer up");
            minecraft.currentScreen.drawTexturedModalRect(164,40,239,19,8,7);
        } else {
            minecraft.currentScreen.drawTexturedModalRect(164,40,248,19,8,7);
        }

        if (isInBox(mouseX,mouseY,164,51,8,7)) {
            tooltip.add("Layer down");
            minecraft.currentScreen.drawTexturedModalRect(164,51,239,29,8,7);
        } else {
            minecraft.currentScreen.drawTexturedModalRect(164,51,248,29,8,7);
        }
        if (isInBox(mouseX,mouseY,164,62,8,8)) {
            tooltip.add("Pause/Play animation");
            if (canTick) minecraft.currentScreen.drawTexturedModalRect(164,62,239,40,8,8);
            else minecraft.currentScreen.drawTexturedModalRect(164,62,239,64,8,8);
        } else {
            if (canTick) minecraft.currentScreen.drawTexturedModalRect(164,62,248,40,8,8);
            else minecraft.currentScreen.drawTexturedModalRect(164,62,248,64,8,8);
        }

        if (multiblock.canRenderFormedStructure()) {
            if (isInBox(mouseX,mouseY,164,74,8,8)) {
                minecraft.currentScreen.drawTexturedModalRect(164,74,239,52,8,8);
                tooltip.add("Show rendered multiblock");
            } else {
                minecraft.currentScreen.drawTexturedModalRect(164,74,248,52,8,8);
            }
        }

        if (isInBox(mouseX,mouseY,164,107,8,8)) {
            tooltip.add("Highlight forming block");
            if (!shouldNotify) minecraft.currentScreen.drawTexturedModalRect(164,107,239,76,8,8);
            else minecraft.currentScreen.drawTexturedModalRect(164,107,239,87,8,8);
        } else {
            if (!shouldNotify) minecraft.currentScreen.drawTexturedModalRect(164,107,248,76,8,8);
            else minecraft.currentScreen.drawTexturedModalRect(164,107,248,87,8,8);
        }


        for (int i = 0;i < materialsRequired.length;i++) {
            minecraft.currentScreen.drawTexturedModalRect(7 + 18*(i%9),127+18*(i/9),238,0,18,18);
        }
        boolean openBuffer = false;
        int stackDepth = GL11.glGetInteger(GL11.GL_MODELVIEW_STACK_DEPTH);
        if (Mouse.isButtonDown(0)) {
            if (!wasClicking) {
                clickX = mouseX;
                clickY = mouseY;
            }
            mouseDragged(clickX,clickY,mouseX,mouseY,lastX,lastY);
        }
        wasClicking = Mouse.isButtonDown(0);
        lastX = mouseX;
        lastY = mouseY;
        try
        {
            if(multiblock.getStructureManual() != null)
            {
                if (++tick % 20 == 0) {
                    if (canTick) renderInfo.step();
                    if (shouldNotify) showNotify = !showNotify;
                }

                int structureLength = renderInfo.structureLength;
                int structureWidth = renderInfo.structureWidth;
                int structureHeight = renderInfo.structureHeight;

                int yOffTotal = (int)(transY-0+scale*Math.sqrt(renderInfo.structureHeight*renderInfo.structureHeight + renderInfo.structureWidth*renderInfo.structureWidth + renderInfo.structureLength*renderInfo.structureLength)/2);

                GlStateManager.enableRescaleNormal();
                GlStateManager.pushMatrix();
                int i = 0;
                ItemStack highlighted = ItemStack.EMPTY;

                final BlockRendererDispatcher blockRender = Minecraft.getMinecraft().getBlockRendererDispatcher();

                float f = (float)Math.sqrt(structureHeight * structureHeight + structureWidth * structureWidth + structureLength * structureLength);

                GlStateManager.translate(transX, transY, Math.max(structureHeight, Math.max(structureWidth, structureLength)));
                GlStateManager.scale(scale, -scale, 1);
                GlStateManager.rotate(rotX, 1, 0, 0);
                GlStateManager.rotate(90+rotY, 0, 1, 0);

                GlStateManager.translate((float)structureLength / -2f, (float)structureHeight / -2f, (float)structureWidth / -2f);

                GlStateManager.disableLighting();

                if(Minecraft.isAmbientOcclusionEnabled())
                    GlStateManager.shadeModel(GL11.GL_SMOOTH);
                else
                    GlStateManager.shadeModel(GL11.GL_FLAT);

                Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                int idx = 0;
                if(showCompleted && multiblock.canRenderFormedStructure())
                    multiblock.renderFormedStructure();
                else
                    for(int h = 0; h < structureHeight; h++)
                        for(int l = 0; l < structureLength; l++)
                            for(int w = 0; w < structureWidth; w++)
                            {
                                BlockPos pos = new BlockPos(l, h, w);
                                if(!blockAccess.isAirBlock(pos))
                                {
                                    int posval = h * structureLength * structureWidth + l * structureWidth + w;
                                    if (shouldNotify && showNotify && posval == triggerPos) {
                                            idx++;
                                            Tessellator tessellator = Tessellator.getInstance();
                                            BufferBuilder buffer = tessellator.getBuffer();
                                            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
                                            openBuffer = true;
                                            blockRender.renderBlock(notifyBlock, pos, blockAccess, buffer);
                                            tessellator.draw();
                                            openBuffer = false;
                                    } else {
                                        GlStateManager.translate(l, h, w);
                                        boolean b = multiblock.overwriteBlockRender(renderInfo.data[h][l][w], idx++);
                                        GlStateManager.translate(-l, -h, -w);
                                        if (!b) {
                                            IBlockState state = blockAccess.getBlockState(pos);
                                            Tessellator tessellator = Tessellator.getInstance();
                                            BufferBuilder buffer = tessellator.getBuffer();
                                            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
                                            openBuffer = true;
                                            blockRender.renderBlock(state, pos, blockAccess, buffer);
                                            tessellator.draw();
                                            openBuffer = false;
                                        }
                                    }
                                }
                            }

                GlStateManager.popMatrix();

                RenderHelper.disableStandardItemLighting();
                GlStateManager.disableRescaleNormal();

                GlStateManager.enableBlend();
                RenderHelper.disableStandardItemLighting();
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        if(openBuffer)
            try{
                Tessellator.getInstance().draw();
            }catch(Exception e){}
        int newStackDepth = GL11.glGetInteger(GL11.GL_MODELVIEW_STACK_DEPTH);
        while (newStackDepth>stackDepth)
        {
            GlStateManager.popMatrix();
            newStackDepth--;
        }
        ClientUtils.drawHoveringText(tooltip, mouseX, mouseY, minecraft.fontRenderer);
    }

    public void mouseDragged(int clickX, int clickY, int mx, int my, int lastX, int lastY) {
        if((clickX>=0 && clickX<159)&&(clickY>=0 && clickY<120)) {
            int dx = mx-lastX;
            int dy = my-lastY;
            rotY = rotY+(dx/104f)*80;
            rotX = rotX+(dy/100f)*80;
        }
    }

    //=============




    class MultiblockRenderInfo
    {
        public MultiblockHandler.IMultiblock multiblock;
        public ItemStack[][][] data;
        public int blockCount = 0;
        public int[] countPerLevel;
        public int structureHeight = 0;
        public int structureLength = 0;
        public int structureWidth = 0;
        public int showLayer = -1;

        private int blockIndex = -1;
        private int maxBlockIndex;

        public MultiblockRenderInfo(MultiblockHandler.IMultiblock multiblock)
        {
            this.multiblock = multiblock;
            init(multiblock.getStructureManual().clone());
            maxBlockIndex = blockIndex = structureHeight * structureLength * structureWidth;
        }

        public void init(ItemStack[][][] structure)
        {
            data = structure;
            structureHeight = structure.length;
            structureWidth = 0;
            structureLength = 0;

            countPerLevel = new int[structureHeight];
            blockCount = 0;
            for(int h = 0; h < structure.length; h++)
            {
                if(structure[h].length > structureLength)
                    structureLength = structure[h].length;
                int perLvl = 0;
                for(int l = 0; l < structure[h].length; l++)
                {
                    if(structure[h][l].length > structureWidth)
                        structureWidth = structure[h][l].length;
                    for(ItemStack ss : structure[h][l])
                        if(ss != null && !ss.isEmpty())
                            perLvl++;
                }
                countPerLevel[h] = perLvl;
                blockCount += perLvl;
            }
        }

        public void setShowLayer(int layer)
        {
            showLayer = layer;
            if(layer<0)
                reset();
            else
                blockIndex = (layer + 1) * (structureLength * structureWidth) - 1;
        }

        public void reset()
        {
            blockIndex = maxBlockIndex;
        }

        public void step()
        {
            int start = blockIndex;
            do
            {
                if(++blockIndex >= maxBlockIndex)
                    blockIndex = 0;
            }
            while(isEmpty(blockIndex) && blockIndex != start);
        }

        private boolean isEmpty(int index)
        {
            int y = index / (structureLength * structureWidth);
            int r = index % (structureLength * structureWidth);
            int x = r / structureWidth;
            int z = r % structureWidth;

            ItemStack stack = data[y][x][z];
            return stack == null || stack.isEmpty();
        }

        public int getLimiter()
        {
            return blockIndex;
        }
    }

    class MultiblockBlockAccess implements IBlockAccess
    {
        private final MultiblockRenderInfo data;
        private final IBlockState[][][] structure;

        public MultiblockBlockAccess(MultiblockRenderInfo data)
        {
            this.data = data;
            final int[] index = {0};//Nasty workaround, but IDEA suggested it =P
            this.structure = Arrays.stream(data.data).map(layer -> {
                return Arrays.stream(layer).map(row -> {
                    return Arrays.stream(row).map(itemstack -> {
                        return convert(index[0]++, itemstack);
                    }).collect(Collectors.toList()).toArray(new IBlockState[0]);
                }).collect(Collectors.toList()).toArray(new IBlockState[0][]);
            }).collect(Collectors.toList()).toArray(new IBlockState[0][][]);
        }

        private IBlockState convert(int index, ItemStack itemstack)
        {
            if (itemstack == null)
                return Blocks.AIR.getDefaultState();
            IBlockState state = data.multiblock.getBlockstateFromStack(index, itemstack);
            if(state!=null)
                return state;
            return Blocks.AIR.getDefaultState();
        }

        @Nullable
        @Override
        public TileEntity getTileEntity(BlockPos pos)
        {
            return null;
        }

        @Override
        public int getCombinedLight(BlockPos pos, int lightValue)
        {
            // full brightness always
            return 15 << 20 | 15 << 4;
        }

        @Override
        public IBlockState getBlockState(BlockPos pos)
        {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            if(y >= 0 && y < structure.length)
                if(x >= 0 && x < structure[y].length)
                    if(z >= 0 && z < structure[y][x].length)
                    {
                        int index = y * (data.structureLength * data.structureWidth) + x * data.structureWidth + z;
                        if(index <= data.getLimiter())
                            return structure[y][x][z];
                    }
            return Blocks.AIR.getDefaultState();
        }

        @Override
        public boolean isAirBlock(BlockPos pos)
        {
            return getBlockState(pos).getBlock() == Blocks.AIR;
        }

        @Override
        public Biome getBiome(BlockPos pos)
        {
            World world = Minecraft.getMinecraft().world;
            if (world!=null)
                return world.getBiome(pos);
            else
                return Biomes.BIRCH_FOREST;
        }

        @Override
        public int getStrongPower(BlockPos pos, EnumFacing direction)
        {
            return 0;
        }

        @Override
        public WorldType getWorldType()
        {

            World world = Minecraft.getMinecraft().world;
            if (world!=null)
                return world.getWorldType();
            else
                return WorldType.DEFAULT;
        }

        @Override
        public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default)
        {
            return false;
        }
    }
}
