package core.common.inventory.slot.fluid;

import core.common.inventory.slot.SlotCoreBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

/**
 * Checks if the fluid container is allowed in the slot.
 * @author Master801
 */
public class SlotFluidContainer extends SlotCoreBase {

	public SlotFluidContainer(IInventory iinventory, int slotIndex, int xCoord, int yCoord) {
		super(iinventory, slotIndex, xCoord, yCoord);
	}

	@Override
	protected boolean isStackValid(ItemStack stack) {
		return FluidContainerRegistry.isContainer(stack) || FluidContainerRegistry.isBucket(stack);
	}

}
