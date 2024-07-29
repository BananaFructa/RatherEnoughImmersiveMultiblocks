package BananaFructa.reim;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class JEIMultiblocCategory implements IRecipeCategory<MultiblockStructureRecipeWrapper> {

    private IDrawable background;

    public JEIMultiblocCategory(IGuiHelper helper) {
        background = helper.createDrawable(new ResourceLocation("reim:background.png"),0,0,176,166);
    }

    @Override
    public String getUid() {
        return REIM.modId + ".multiblock";
    }

    @Override
    public String getTitle() {
        return "Multiblock Structure";
    }

    @Override
    public String getModName() {
        return REIM.modId;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MultiblockStructureRecipeWrapper multiblockStructureRecipeWrapper, IIngredients iIngredients) {
        ItemStack[] mats = multiblockStructureRecipeWrapper.materialsRequired;
        IGuiItemStackGroup guiItemStackGroup = iRecipeLayout.getItemStacks();
        for (int i = 0;i < mats.length;i++) {
            guiItemStackGroup.init(i,true,7 + 18*(i%9),127+18*(i/9));
            guiItemStackGroup.set(i,mats[i]);
        }
        guiItemStackGroup.init(mats.length,true,159,1);
        guiItemStackGroup.set(mats.length,multiblockStructureRecipeWrapper.multiblockItem);
    }
}
