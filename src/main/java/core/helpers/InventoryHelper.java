package core.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * To make my life, easier.
 * @author Master801
 */
public final class InventoryHelper {

	public static ItemStack decrStackSize(int i, int j, ItemStack[] inventory, IInventory iinventory) {
		if (inventory[i] != null) {
			ItemStack itemstack;
			if (inventory[i].stackSize <= j) {
				itemstack = inventory[i];
				inventory[i] = null;
				iinventory.markDirty();
				return itemstack;
			} else {
				itemstack = inventory[i].splitStack(j);
				if (inventory[i].stackSize == 0) {
					inventory[i] = null;
				}
				iinventory.markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	public static ItemStack getStackInSlotOnClosing(int i, ItemStack[] inventory) {
		if (inventory[i] != null) {
			final ItemStack itemstack = inventory[i];
			inventory[i] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	public static void setInventorySlotContents(int i, ItemStack stack, ItemStack[] inventory, IInventory iinventory) {
		inventory[i] = stack;
		if (stack != null && stack.stackSize > iinventory.getInventoryStackLimit()) {
			stack.stackSize = iinventory.getInventoryStackLimit();
		}
		iinventory.markDirty();
	}

	public static boolean isUseableByPlayer(EntityPlayer player, int xCoord, int yCoord, int zCoord) {
		return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D;
	}

	public static void writeToNBT(NBTTagCompound nbt, ItemStack[] inventory) {
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; ++i) {
			if (inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", RandomHelper.convertIntegerToByte(i));
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}

	public static void readFromNBT(NBTTagCompound nbt, ItemStack[] inventory) {
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int b0 = nbttagcompound1.getByte("Slot");
			if (b0 >= 0 && b0 < inventory.length) {
				inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	/**
	 * This is more of a joke really.
	 */
	public static ItemStack getStackInSlot(int slot, ItemStack[] inventory) {
		return inventory[slot];
	}

}
