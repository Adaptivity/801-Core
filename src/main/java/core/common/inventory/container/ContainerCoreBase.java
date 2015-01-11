package core.common.inventory.container;

import core.Core;
import core.api.tileentity.IGuiUpdateData;
import core.common.resources.CoreEnums.LoggerEnum;
import core.helpers.LoggerHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

/**
 * Created by Master801 on 11/15/2014.
 * @author Master801
 */
public abstract class ContainerCoreBase<T extends IInventory> extends Container {

    private final T inventory;
    public final List<Slot> inventorySlots = (List<Slot>)super.inventorySlots;

    public ContainerCoreBase(InventoryPlayer playerInventory, T inventory) {
        this.inventory = inventory;
        addSlots(this.inventory);
        addPlayerInventorySlots(playerInventory);
    }

    public final int getNextSlotID(int wantedID) {
        try {
            if (getSlot(wantedID) == null) {
                return wantedID;
            }
        } catch(Exception exception) {
            return wantedID;
        }
        int bufferID = wantedID + 1;
        boolean doesCheck = true;
        do {
            if (getSlot(bufferID) == null) {
                doesCheck = false;
                return bufferID;
            } else if (getSlot(bufferID) != null) {
                doesCheck = true;
                bufferID++;
            } else {
                doesCheck = false;
                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "Failed to get the next available slot id! Class: '%s' ID: '%d'", getClass().toString(), bufferID);
            }
        } while(doesCheck);
        return 0;
    }

    protected Slot createNewSlotInstance(IInventory inventory, int wantedID, int slotX, int slotY) {
        return new Slot(inventory, getNextSlotID(wantedID), slotX, slotY);
    }

    protected final void addSlotToContainer(IInventory inventory, int wantedID, int slotX, int slotY) {
        addSlotToContainer(createNewSlotInstance(inventory, wantedID, slotX, slotY));
    }

    /**
     * Add your custom slots in this method.
     */
    protected void addSlots(IInventory inventory) {
    }

    protected void addPlayerInventorySlots(InventoryPlayer playerInventory) {
        for(int column = 0; column < 3; column++) {
            for(int row = 0; row < 9; row++) {
                addSlotToContainer(new Slot(playerInventory, row + column * 9 + 9, (row * 18) + 8, (column * 18) + 84));
            }
        }
        for(int heldSlot = 0; heldSlot < 9; heldSlot++) {
            addSlotToContainer(new Slot(playerInventory, heldSlot, (heldSlot * 18) + 8, 142));
        }
    }

    @Override
    protected final Slot addSlotToContainer(Slot slot) {
        return super.addSlotToContainer(slot);
    }

    public final int getSlotNumberFromSlot(Slot slot) {
        int slotNumber = Integer.MAX_VALUE;
        if (slot == null) {
            return slotNumber;
        }
        if (!getInventorySlots().contains(slot)) {
            return slotNumber;
        }
        for(Slot gotSlot : getInventorySlots()) {
            if (gotSlot == slot) {
                return gotSlot.getSlotIndex();
            }
        }
        return slotNumber;
    }

    public final List<ICrafting> getCrafters() {
        return (List<ICrafting>) crafters;
    }

    public final List<Slot> getInventorySlots() {
        return inventorySlots;
    }

    public final T getIInventory() {
        return inventory;
    }

    public final TileEntity getTileEntity() {
        if (getIInventory() instanceof TileEntity) {
            return (TileEntity)getIInventory();
        }
        return null;
    }

    @Override
    public void updateProgressBar(int id, int value) {
        if (getTileEntity() instanceof IGuiUpdateData) {
            ((IGuiUpdateData)getTileEntity()).readFromGuiData(id, value);
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (getTileEntity() instanceof IGuiUpdateData) {
            for(ICrafting crafting : getCrafters()) {
                ((IGuiUpdateData)getTileEntity()).writeToGuiData(this, crafting);
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        ItemStack stack = null;
        Slot slot = getSlot(slotNumber);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();
            if (getIInventory() != null && getIInventory().getSizeInventory() > 0) {
                if (slotNumber < getIInventory().getSizeInventory()) {
                    if (!mergeItemStack(slotStack, getIInventory().getSizeInventory(), inventorySlots.size(), true)) {
                        return null;
                    }
                } else if (!mergeItemStack(slotStack, 0, getIInventory().getSizeInventory(), false)) {
                    return null;
                }
            }
            if (slotStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (getIInventory() != null) {
            return getIInventory().isUseableByPlayer(player);
        }
        return true;
    }

}
