package BananaFructa.reim;

import blusunrize.immersiveengineering.api.MultiblockHandler;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.multiblocks.*;
import com.pyraliron.advancedtfctech.multiblocks.MultiblockGristMill;
import com.pyraliron.advancedtfctech.multiblocks.MultiblockPowerLoom;
import com.pyraliron.advancedtfctech.multiblocks.MultiblockThresher;
import flaxbeard.immersivepetroleum.common.blocks.multiblocks.MultiblockDistillationTower;
import flaxbeard.immersivepetroleum.common.blocks.multiblocks.MultiblockPumpjack;
import mctmods.immersivetechnology.common.blocks.metal.multiblocks.*;
import mctmods.immersivetechnology.common.util.compat.jei.GenericMultiblockIngredient;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import pl.pabilo8.immersiveintelligence.common.block.multiblock.metal_multiblock0.multiblock.*;
import pl.pabilo8.immersiveintelligence.common.block.multiblock.metal_multiblock1.multiblock.*;
import team.cappcraft.immersivechemical.common.blocks.multiblocks.MultiBlockHeatExchangerLarge;
import team.cappcraft.immersivechemical.common.blocks.multiblocks.MultiBlockHeatExchangerMedium;
import team.cappcraft.immersivechemical.common.blocks.multiblocks.MultiBlockHeatExchangerSmall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    public static IIngredientRegistry itemRegistry;
    public static IJeiHelpers jeiHelper;

    public static JEIMultiblocCategory multiblockCategory;

    List<MultiblockStructureRecipeWrapper> structureWrappers = new ArrayList<>();

    public MultiblockInfo[] multiblockRegistry;

    boolean immersivePetroleum = Loader.isModLoaded("immersivepetroleum");
    boolean immersiveTech = Loader.isModLoaded("immersivetech");
    boolean advancedTfcTech = Loader.isModLoaded("att");
    boolean immersiveIntelligence = Loader.isModLoaded("immersiveintelligence");
    boolean immersiveChemical = Loader.isModLoaded("immersivechemical");

    public JEIPlugin() {

    }

    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
    }

    public void registerIngredients(IModIngredientRegistration registry) {
    }

    public void registerCategories(IRecipeCategoryRegistration registry) {
        multiblockCategory = new JEIMultiblocCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(multiblockCategory);
    }

    public void register(IModRegistry registry) {

        multiblockRegistry = new ArrayList<MultiblockInfo>(){{
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock>",MultiblockMetalPress.instance,4));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:1>", MultiblockCrusher.instance,27));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:2>", MultiblockSheetmetalTank.instance,25));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:3>",MultiblockSilo.instance,25));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:4>", MultiblockAssembler.instance,10));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:5>", MultiblockAutoWorkbench.instance,10));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:6>", MultiblockBottlingMachine.instance,7));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:7>", MultiblockSqueezer.instance,13));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:8>", MultiblockFermenter.instance,13));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:9>", MultiblockRefinery.instance,17));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:10>", MultiblockDieselGenerator.instance,16));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:11>", MultiblockExcavatorDemo.instance,73));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:13>", MultiblockArcFurnace.instance,2));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:14>", MultiblockLightningrod.instance,10));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:15>", MultiblockMixer.instance,13));
                add(new MultiblockInfo("<immersiveengineering:stone_device>", MultiblockCokeOven.instance,16));
                add(new MultiblockInfo("<immersiveengineering:stone_device:1>", MultiblockBlastFurnace.instance,16));
                add(new MultiblockInfo("<immersiveengineering:stone_device:2>", MultiblockBlastFurnaceAdvanced.instance,16));
                add(new MultiblockInfo("<immersiveengineering:stone_device:7>",MultiblockAlloySmelter.instance,1));
                if (immersivePetroleum) {
                    add(new MultiblockInfo("<immersivepetroleum:metal_multiblock:3>", MultiblockPumpjack.instance,22));
                    add(new MultiblockInfo("<immersivepetroleum:metal_multiblock:1>", MultiblockDistillationTower.instance,28));
                }
                if (immersiveTech) {

                    add(new MultiblockInfo("<immersivetech:metal_multiblock>", MultiblockDistiller.instance,13));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock>"),"it.distiller");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock:3>", MultiblockSteamTurbine.instance,31));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock:3>"),"it.steamTurbine");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock:1>", MultiblockSolarTower.instance,22));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock:1>"),"it.solarTower");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock:4>", MultiblockBoiler.instance,17));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock:4>"),"it.boiler","it.boilerFuel");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock:14>", MultiblockCoolingTower.instance,4));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock:14>"),"it.coolingTower");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1>", MultiblockGasTurbine.instance,25));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1>"),"it.gasTurbine");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1:2>", MultiblockHeatExchanger.instance,17));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1:2>"),"it.heatExchanger");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1:6>", MultiblockElectrolyticCrucibleBattery.instance,1));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1:6>"),"it.electrolyticCrucibleBattery");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1:8>", MultiblockMeltingCrucible.instance,13));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1:8>"),"it.meltingCrucible");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1:10>", MultiblockRadiator.instance,27));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1:10>"),"it.radiator");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1:12>", MultiblockSolarMelter.instance,31));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1:12>"),"it.meltingCrucible");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock:5>", MultiblockAlternator.instance,13));
                    add(new MultiblockInfo("<immersivetech:metal_multiblock:2>", MultiblockSolarReflector.instance,13));
                    add(new MultiblockInfo("<immersivetech:metal_multiblock:12>", MultiblockSteelSheetmetalTank.instance,25));
                }
                if (advancedTfcTech) {
                    add(new MultiblockInfo("<att:metal_multiblock_att:1>", MultiblockPowerLoom.instance,16));
                    add(new MultiblockInfo("<att:metal_multiblock_att:3>", MultiblockThresher.instance,10));
                    add(new MultiblockInfo("<att:metal_multiblock_att:5>", MultiblockGristMill.instance,17));
                }
                if (immersiveIntelligence) {
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock>", MultiblockRadioStation.INSTANCE,9));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:1>", MultiblockPrintingPress.INSTANCE,16));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:2>", MultiblockDataInputMachine.INSTANCE,4));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:3>", MultiblockArithmeticLogicMachine.INSTANCE,8));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:4>", MultiblockChemicalBath.INSTANCE,22));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:5>", MultiblockElectrolyzer.INSTANCE,10));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:6>", MultiblockPrecisionAssembler.INSTANCE,2));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:7>", MultiblockBallisticComputer.INSTANCE,0));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:8>", MultiblockArtilleryHowitzer.INSTANCE,409));

                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:10>", MultiblockScanningConveyor.INSTANCE,1));


                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:13>", MultiblockPacker.INSTANCE,13));

                    //

                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1>", MultiblockRedstoneInterface.INSTANCE,0));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:1>", MultiblockFiller.INSTANCE,10));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:2>", MultiblockCoagulator.INSTANCE,38));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:3>", MultiblockProjectileWorkshop.INSTANCE,14));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:4>", MultiblockAmmunitionAssembler.INSTANCE,1));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:5>", MultiblockFuelStation.INSTANCE,6));
                    //add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:6>", MultiblockAmmunitionAssembler.INSTANCE,1));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:7>", MultiblockFlagpole.INSTANCE,28));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:8>", MultiblockRadar.INSTANCE,4));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:9>", MultiblockEmplacement.INSTANCE,37));

                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:11>", MultiblockChemicalPainter.INSTANCE,2));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:12>", MultiblockVulcanizer.INSTANCE,26));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:13>", MultiblockHeavyAmmunitionAssembler.INSTANCE,12));
                }
                if (immersiveChemical) {
                    add(new MultiblockInfo("<immersivechemical:multiblock_heat_exchanger>", MultiBlockHeatExchangerSmall.INSTANCE,0));
                    add(new MultiblockInfo("<immersivechemical:multiblock_heat_exchanger:1>", MultiBlockHeatExchangerMedium.INSTANCE,10));
                    add(new MultiblockInfo("<immersivechemical:multiblock_heat_exchanger:2>", MultiBlockHeatExchangerLarge.INSTANCE,22));
                }
        }}.toArray(new MultiblockInfo[0]);

        itemRegistry = registry.getIngredientRegistry();
        jeiHelper = registry.getJeiHelpers();

        for (MultiblockInfo m : multiblockRegistry) {
           structureWrappers.add(new MultiblockStructureRecipeWrapper(m.multiblock,Utils.itemStackFromCTId(m.itemId),m.triggerPos));
        }

        registry.addRecipes(structureWrappers,REIM.modId+".multiblock");

        registry.addRecipeCategoryCraftingItem(new ItemStack(IEContent.itemTool,1),multiblockCategory.getUid());
        if (immersiveIntelligence) {
            registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersiveintelligence:electric_hammer>"),multiblockCategory.getUid());
        }
    }

    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        for (MultiblockInfo m : multiblockRegistry) {
            addItemStackToJEI(m.itemId);
        }
        if (immersiveTech) {
            for (GenericMultiblockIngredient ingredient : GenericMultiblockIngredient.list) {
                jeiHelper.getIngredientBlacklist().addIngredientToBlacklist(ingredient);
            }
        }
    }

    public static void addItemStackToJEI(String ctLikeString) {
        ItemStack stack = Utils.itemStackFromCTId(ctLikeString).copy();
        jeiHelper.getIngredientBlacklist().removeIngredientFromBlacklist(new ItemStack(stack.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        jeiHelper.getIngredientBlacklist().removeIngredientFromBlacklist(stack);
        itemRegistry.addIngredientsAtRuntime(VanillaTypes.ITEM, Collections.singletonList(stack));
    }

    static class MultiblockInfo {
        public String itemId;
        public MultiblockHandler.IMultiblock multiblock;
        public int triggerPos;
        public MultiblockInfo(String itemId, MultiblockHandler.IMultiblock multiblock, int triggerPos) {
            this.itemId = itemId;
            this.multiblock = multiblock;
            this.triggerPos = triggerPos;
        }
    }

}
