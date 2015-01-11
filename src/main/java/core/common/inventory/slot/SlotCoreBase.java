package core.common.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Master801
 */
public abstract class SlotCoreBase extends Slot {
	
	protected abstract boolean isStackValid(ItemStack stack);

	public SlotCoreBase(IInventory iinventory, int slotIndex, int xCoord, int yCoord) {
		super(iinventory, slotIndex, xCoord, yCoord);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return isStackValid(stack);
	}

}
