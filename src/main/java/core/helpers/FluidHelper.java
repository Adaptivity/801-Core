package core.helpers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

/**
 * @author Master801
 */
public final class FluidHelper {

	private static final Map<String, Fluid> FLUID_MAP = new HashMap<String, Fluid>();

	/**
	 * Used for decreasing (destroying?) fluid container items.
	 */
	public static boolean decreaseFluidStack(EntityPlayer player, ItemStack stack) {
		if (player.capabilities.isCreativeMode) {
			return true;
		}
		boolean didRun = false;
		for(int i = 0; i < player.inventory.mainInventory.length; i++) {
			final ItemStack inventoryStack = player.inventory.mainInventory[i];
			if (inventoryStack == stack && stack.getMaxStackSize() == 1) {
				player.inventory.mainInventory[i] = null;
			} else {
				--player.inventory.mainInventory[i].stackSize;
			}
			didRun = true;
		}
		return didRun;
	}

	public static Fluid getFluid(String fluidName) {
		Fluid fluid = FLUID_MAP.get(fluidName);
		if (fluid == null) {
			fluid = new Fluid(fluidName);
            FLUID_MAP.put(fluidName, fluid);
		}
		return fluid;
	}

}
