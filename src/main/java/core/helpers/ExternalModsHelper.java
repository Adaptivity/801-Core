package core.helpers;

import core.Core;
import core.api.common.mod.IMod;
import core.common.resources.CoreEnums.LoggerEnum;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import cpw.mods.fml.common.Mod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.lang.reflect.Field;

public final class ExternalModsHelper {

	/**
	 * This only makes to new multipart blocks. Not including tile-entities!
	 */
	public static void createNewMultipartBlock(Block block, int metadata, String multipartName) {
		boolean didInvoke = true;
		didInvoke = ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("codechicken.microblock.BlockMicroMaterial", "createAndRegister", Block.class, int.class, String.class), null, block, metadata, multipartName) != null;
		if (didInvoke) {
			LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.INFO, "Successfully found ForgeMultipart, and created a new Multipart!");
		} else {
			LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.INFO, "Did not find ForgeMultipart...");
		}
	}

	public static ItemStack getIC2ItemStack(String name) {
		if (name == null) {
			return null;
		}
		ItemStack gotStack = ReflectionHelper.getFieldValue(ReflectionHelper.getField("ic2.core.Ic2Items", name), null);
		if (gotStack == null) {
			throw new CoreNullPointerException("The ItemStack got from IC2, is null! Item: '%s'.", name);
		}
		return gotStack;
	}

    public static ItemStack getIC2ClassicItemStack(String name) {
        if (name == null) {
            return null;
        }
        ItemStack gotStack = ReflectionHelper.getFieldValue(ReflectionHelper.getField("ic2classic.core.Ic2Items", name), null);
        if (gotStack == null) {
            throw new CoreNullPointerException("The ItemStack got from IC2-Classic is null! Item: '%s'", name);
        }
        return gotStack;
    }

    public static void setIC2ItemStack(String fieldName, ItemStack newItemStack) {
        ReflectionHelper.setFieldValue(ReflectionHelper.getField("ic2.core.Ic2Items", fieldName), null, newItemStack);
    }

    public static void setIC2ClassItemStack(String fieldName, ItemStack newItemStack) {
        ReflectionHelper.setFieldValue(ReflectionHelper.getField("ic2classic.core.Ic2Items", fieldName), null, newItemStack);
    }

	public static String getModIDFromClass(Class<?> modClass) {
        if (modClass != null) {
            Mod modAnnon = modClass.getAnnotation(Mod.class);
            if (modAnnon == null) {
                throw new CoreNullPointerException("The Class file is NOT a mod! Unknown Class File: '%s'.", modClass.getCanonicalName());
            }
            return modAnnon.modid();
        }
        return null;
	}

    public static boolean explodeIC2ClassicMachine(TileEntity tile) {
        try {
            Class.forName("ic2classic.core.IC2").getDeclaredMethod("explodeMachineAt", World.class, int.class, int.class, int.class).invoke(null, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
            return true;//IC2-Classic is installed.
        } catch(Exception exception) {
            return false;//Ignore this method if IC2-Classic is not installed.
        }
    }

    /**
     * <p>
     *     Master801: This JavaDoc was taken from the original method.
     *     tconstruct.library.crafting.Smeltery.addMelting(ItemStack input, Block block, int metadata, int temperature, FluidStack liquid)
     * </p>
     * Adds mappings between an input and its liquid. Renders with the given
     * input's block ID and metadata Example: Smeltery.addMelting(Block.oreIron,
     * 0, 600, new FluidStack(liquidMetalStill.blockID,
     * TConstruct.ingotLiquidValue * 2, 0));
     *
     * @param input The item to liquify
     * @param block The block to render
     * @param metadata The metadata of the block to render
     * @param temperature How hot the block should be before liquifying
     * @param output The result of the process
     */
    public static void addSmelteryRecipe(ItemStack input, Block block, int metadata, int temperature, FluidStack output) {
        ReflectionHelper.invokeMethod(ReflectionHelper.getMethod(ReflectionHelper.getClassFromString("tconstruct.library.crafting.Smeltery"), "addMelting", ItemStack.class, Block.class, int.class, int.class, FluidStack.class), null, input, block, metadata, temperature, output);
    }

    private static Object[] getCogPounderRecipeArray(ItemStack[] outputStacks, int[] outputPercentages) {
        Object[] recipeArray = new Object[outputStacks.length * outputPercentages.length];
        for(int i = 0; i < recipeArray.length / 2; i++) {
            if (outputStacks[i] != null) {
                recipeArray[i] = outputStacks[i];
            }
            if (outputPercentages[i] > -1) {
                recipeArray[i + 1] = outputPercentages[i + 1];
            }
        }
        return recipeArray;
    }

    public static void addStonePounderRecipe(Block inputBlock, int blockMetadata, ItemStack[] outputStacks, int[] outputPercentages) {
        ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("deatrathias.cogs.machine.PounderRecipes", "addRecipe", Block.class, int.class, Object[].class), ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("deatrathias.cogs.machine.PounderRecipes", "getInstance"), null), inputBlock, blockMetadata, ExternalModsHelper.getCogPounderRecipeArray(outputStacks, outputPercentages));
    }

    public static void addStonePounderRecipe(String oreDictionaryName, ItemStack[] outputStacks, int[] outputPercentages) {
        ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("deatrathias.cogs.machine.PounderRecipes", "addRecipe", String.class, Object[].class), ReflectionHelper.invokeMethod(ReflectionHelper.getMethod("deatrathias.cogs.machine.PounderRecipes", "getInstance"), null), oreDictionaryName, ExternalModsHelper.getCogPounderRecipeArray(outputStacks, outputPercentages));
    }

    public static Field[] getAllCogItems() {
        return ReflectionHelper.getFields("deatrathias.cogs.util.ItemLoader");
    }

    public static Item getCogsCogwheelItem() {
        return ReflectionHelper.getFieldValue(ReflectionHelper.getField("deatrathias.cogs.util.ItemLoader", "itemCogwheel"), null);
    }

    public static ItemStack getCogStack(String fieldName) {
        return ExternalModsHelper.getCogStack(fieldName, 1, 0);
    }

    public static ItemStack getCogStack(String fieldName, int metadata) {
        return ExternalModsHelper.getCogStack(fieldName, 1, metadata);
    }

    public static ItemStack getCogStack(String fieldName, int stackSize, int metadata) {
        if (fieldName == null) {
            return null;
        }
        Object unknownObject = ReflectionHelper.getFieldValue(ReflectionHelper.getField("deatrathias.cogs.util.ItemLoader", fieldName), null);
        if (unknownObject == null) {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Failed to get Cogs' object! Field: '%s'", fieldName);
            return null;
        }
        if (unknownObject instanceof ItemStack) {
            return (ItemStack)unknownObject;
        } else if (unknownObject instanceof Item) {
            return new ItemStack((Item)unknownObject, stackSize, metadata);
        } else if (unknownObject instanceof Block) {
            return new ItemStack((Block)unknownObject, stackSize, metadata);
        }
        return null;
    }

}
