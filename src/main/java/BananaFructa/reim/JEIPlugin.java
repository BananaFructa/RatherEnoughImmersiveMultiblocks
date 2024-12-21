package BananaFructa.reim;

import BananaFructa.TTIEMultiblocks.Compat.jei.TTCategory;
import BananaFructa.TTIEMultiblocks.TTIEContent;
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
    public static JEIMultiblocCategory tiagThingsFireplaceMultiblockCategory;

    List<MultiblockStructureRecipeWrapper> structureWrappers = new ArrayList<>();
    List<MultiblockStructureRecipeWrapper> tiagThingsStructureWrappers = new ArrayList<>();

    public MultiblockInfo[] multiblockRegistry;
    public MultiblockInfo[] tiagThingsMultiblockRegistry;

    boolean immersivePetroleum = Loader.isModLoaded("immersivepetroleum");
    boolean immersiveTech = Loader.isModLoaded("immersivetech");
    boolean advancedTfcTech = Loader.isModLoaded("att");
    boolean immersiveIntelligence = Loader.isModLoaded("immersiveintelligence");
    boolean immersiveChemical = Loader.isModLoaded("immersivechemical");
    boolean tiagThings = Loader.isModLoaded("tiagthings");

    public JEIPlugin() {

    }

    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
    }

    public void registerIngredients(IModIngredientRegistration registry) {
    }

    public void registerCategories(IRecipeCategoryRegistration registry) {
        multiblockCategory = new JEIMultiblocCategory(registry.getJeiHelpers().getGuiHelper());
        registry.addRecipeCategories(multiblockCategory);
        if (tiagThings) {
            tiagThingsFireplaceMultiblockCategory = new JEIMultiblocCategory(registry.getJeiHelpers().getGuiHelper()) {
                @Override
                public String getUid() {
                    return REIM.modId + ".tiagThingsMultiblock";
                }
            };
            registry.addRecipeCategories(tiagThingsFireplaceMultiblockCategory);
        }
    }

    public void register(IModRegistry registry) {

        if (tiagThings) {
            registry.addIngredientInfo(Utils.itemStackFromCTId("<tiagthings:tt_metal_multiblock_2:4>"), VanillaTypes.ITEM, "To form primitive multiblocks shift+right click on the side of the fire pit.");
            registry.addIngredientInfo(Utils.itemStackFromCTId("<tiagthings:tt_metal_multiblock_2:6>"), VanillaTypes.ITEM, "To form primitive multiblocks shift+right click on the side of the fire pit.");
        }
        multiblockRegistry = new ArrayList<MultiblockInfo>(){{
            try {
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock>", MultiblockMetalPress.instance, 4));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:1>", MultiblockCrusher.instance, 27));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:2>", MultiblockSheetmetalTank.instance, 25));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:3>", MultiblockSilo.instance, 25));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:4>", MultiblockAssembler.instance, 10));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:5>", MultiblockAutoWorkbench.instance, 10));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:6>", MultiblockBottlingMachine.instance, 7));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:7>", MultiblockSqueezer.instance, 13));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:8>", MultiblockFermenter.instance, 13));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:9>", MultiblockRefinery.instance, 17));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:10>", MultiblockDieselGenerator.instance, 16));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:11>", MultiblockExcavatorDemo.instance, 73));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:13>", MultiblockArcFurnace.instance, 2));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:14>", MultiblockLightningrod.instance, 10));
                add(new MultiblockInfo("<immersiveengineering:metal_multiblock:15>", MultiblockMixer.instance, 13));
                add(new MultiblockInfo("<immersiveengineering:stone_device>", MultiblockCokeOven.instance, 16));
                add(new MultiblockInfo("<immersiveengineering:stone_device:1>", MultiblockBlastFurnace.instance, 16));
                add(new MultiblockInfo("<immersiveengineering:stone_device:2>", MultiblockBlastFurnaceAdvanced.instance, 16));
                add(new MultiblockInfo("<immersiveengineering:stone_device:7>", MultiblockAlloySmelter.instance, 1));
            } catch (Exception err) {
                err.printStackTrace();
            }
            try {
                if (immersivePetroleum) {
                    add(new MultiblockInfo("<immersivepetroleum:metal_multiblock:3>", MultiblockPumpjack.instance, 22));
                    add(new MultiblockInfo("<immersivepetroleum:metal_multiblock:1>", MultiblockDistillationTower.instance, 28));
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            try {
                if (immersiveTech) {

                    add(new MultiblockInfo("<immersivetech:metal_multiblock>", MultiblockDistiller.instance, 13));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock>"), "it.distiller");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock:3>", MultiblockSteamTurbine.instance, 31));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock:3>"), "it.steamTurbine");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock:1>", MultiblockSolarTower.instance, 22));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock:1>"), "it.solarTower");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock:4>", MultiblockBoiler.instance, 17));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock:4>"), "it.boiler", "it.boilerFuel");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock:14>", MultiblockCoolingTower.instance, 4));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock:14>"), "it.coolingTower");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1>", MultiblockGasTurbine.instance, 25));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1>"), "it.gasTurbine");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1:2>", MultiblockHeatExchanger.instance, 17));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1:2>"), "it.heatExchanger");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1:6>", MultiblockElectrolyticCrucibleBattery.instance, 1));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1:6>"), "it.electrolyticCrucibleBattery");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1:8>", MultiblockMeltingCrucible.instance, 13));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1:8>"), "it.meltingCrucible");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1:10>", MultiblockRadiator.instance, 27));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1:10>"), "it.radiator");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock1:12>", MultiblockSolarMelter.instance, 31));
                    registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<immersivetech:metal_multiblock1:12>"), "it.meltingCrucible");

                    add(new MultiblockInfo("<immersivetech:metal_multiblock:5>", MultiblockAlternator.instance, 13));
                    add(new MultiblockInfo("<immersivetech:metal_multiblock:2>", MultiblockSolarReflector.instance, 13));
                    add(new MultiblockInfo("<immersivetech:metal_multiblock:12>", MultiblockSteelSheetmetalTank.instance, 25));
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            try {
                if (advancedTfcTech) {
                    add(new MultiblockInfo("<att:metal_multiblock_att:1>", MultiblockPowerLoom.instance, 16));
                    add(new MultiblockInfo("<att:metal_multiblock_att:3>", MultiblockThresher.instance, 10));
                    add(new MultiblockInfo("<att:metal_multiblock_att:5>", MultiblockGristMill.instance, 17));
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            try {
                if (immersiveIntelligence) {
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock>", MultiblockRadioStation.INSTANCE, 9));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:1>", MultiblockPrintingPress.INSTANCE, 16));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:2>", MultiblockDataInputMachine.INSTANCE, 4));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:3>", MultiblockArithmeticLogicMachine.INSTANCE, 8));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:4>", MultiblockChemicalBath.INSTANCE, 22));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:5>", MultiblockElectrolyzer.INSTANCE, 10));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:6>", MultiblockPrecisionAssembler.INSTANCE, 2));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:7>", MultiblockBallisticComputer.INSTANCE, 0));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:8>", MultiblockArtilleryHowitzer.INSTANCE, 409));

                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:10>", MultiblockScanningConveyor.INSTANCE, 1));


                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock:13>", MultiblockPacker.INSTANCE, 13));

                    //

                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1>", MultiblockRedstoneInterface.INSTANCE, 0));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:1>", MultiblockFiller.INSTANCE, 10));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:2>", MultiblockCoagulator.INSTANCE, 38));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:3>", MultiblockProjectileWorkshop.INSTANCE, 14));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:4>", MultiblockAmmunitionAssembler.INSTANCE, 1));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:5>", MultiblockFuelStation.INSTANCE, 6));
                    //add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:6>", MultiblockAmmunitionAssembler.INSTANCE,1));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:7>", MultiblockFlagpole.INSTANCE, 28));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:8>", MultiblockRadar.INSTANCE, 4));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:9>", MultiblockEmplacement.INSTANCE, 37));

                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:11>", MultiblockChemicalPainter.INSTANCE, 2));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:12>", MultiblockVulcanizer.INSTANCE, 26));
                    add(new MultiblockInfo("<immersiveintelligence:metal_multiblock1:13>", MultiblockHeavyAmmunitionAssembler.INSTANCE, 12));
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            try {
                if (immersiveChemical) {
                    add(new MultiblockInfo("<immersivechemical:multiblock_heat_exchanger>", MultiBlockHeatExchangerSmall.INSTANCE, 0));
                    add(new MultiblockInfo("<immersivechemical:multiblock_heat_exchanger:1>", MultiBlockHeatExchangerMedium.INSTANCE, 10));
                    add(new MultiblockInfo("<immersivechemical:multiblock_heat_exchanger:2>", MultiBlockHeatExchangerLarge.INSTANCE, 22));
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
            try {
                if (tiagThings) {
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock>", TTIEContent.coalBoilerMultiblock, 75));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock:2>", TTIEContent.electricHeaterMultiblock, 1));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock:4>", TTIEContent.flareStackMultiblock, 7));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock:6>", TTIEContent.clarifierMultiblock, 357));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock:8>", TTIEContent.waterFilter, 4));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock:10>", TTIEContent.oilBoilerMultiblock, 75));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock:12>", TTIEContent.umPhotolithographyMachine, 4));

                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_1>", TTIEContent.nmPhotolitographyMachine, 17));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_1:2>", TTIEContent.euvPhotolitographyMachine, 39));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_1:4>", TTIEContent.gasCentrifugeMultiblock, 0));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_1:6>", TTIEContent.steamRadiatorMultiblock, 1));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_1:8>", TTIEContent.indoorAcUnitMultiblock, 1));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_1:10>", TTIEContent.outdoorAcUnitMultiblock, 1));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_1:14>", TTIEContent.electricOvenMultiblock, 17));

                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_2>", TTIEContent.siliconCrucible, 0));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_2:11>", TTIEContent.magneticSeparator, 5));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_2:13>", TTIEContent.openHearthFurnace, 592));

                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_3>", TTIEContent.shaftFurnace, 22));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_3:2>", TTIEContent.fbr, 7));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_3:4>", TTIEContent.ccm, 15));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_3:6>", TTIEContent.smallCoalBoiler, 22));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_3:8>", TTIEContent.steamEngine, 6));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_3:10>", TTIEContent.lathe, 4));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_3:12>", TTIEContent.metalRoller, 1));

                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_4>", TTIEContent.cokeOvenBattery, 167));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_4:2>", TTIEContent.electricFoodOven, 38));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_4:4>", TTIEContent.magnetizer, 0));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_4:6>", TTIEContent.cokerUnit, 79));
                }
            } catch (Exception err) {
                err.printStackTrace();
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
        if (tiagThings) {
            tiagThingsMultiblockRegistry = new ArrayList<MultiblockInfo>(){{
                try {
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_2:4>",TTIEContent.clayOven,4));
                    add(new MultiblockInfo("<tiagthings:tt_metal_multiblock_2:6>",TTIEContent.masonryHeater,13));
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }}.toArray(new MultiblockInfo[0]);

            for (MultiblockInfo m : tiagThingsMultiblockRegistry) {
                tiagThingsStructureWrappers.add(new MultiblockStructureRecipeWrapper(m.multiblock,Utils.itemStackFromCTId(m.itemId),m.triggerPos));
            }

            registry.addRecipes(tiagThingsStructureWrappers,REIM.modId+".tiagThingsMultiblock");

            registry.addRecipeCategoryCraftingItem(Utils.itemStackFromCTId("<tfc:firepit>"),tiagThingsFireplaceMultiblockCategory.getUid());
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
        if (tiagThings) {
            for (MultiblockInfo m : tiagThingsMultiblockRegistry) {
                addItemStackToJEI(m.itemId);
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
