package core.tileentity;

import core.api.client.gui.IGuiHelper;
import core.api.common.IGUId;
import core.api.item.tool.ITool;
import core.api.tileentity.IActivate;
import core.api.tileentity.INBTTagCompound;
import core.api.tileentity.IRotatable;
import core.helpers.InventoryHelper;
import core.helpers.PlayerHelper;
import core.helpers.RotationHelper;
import core.helpers.TileEntityHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.UUID;

/**
 * Core tile entity for all of Master801's mods.
 * @author Master801
 */
public abstract class TileEntityCoreBase extends TileEntity implements IGuiHelper, IGUId, IActivate, IRotatable, INBTTagCompound {

	private byte rotation = RotationHelper.convertForgeDirectionToByte(ForgeDirection.UP);

	public boolean isUseableByPlayer(EntityPlayer player) {
		return InventoryHelper.isUseableByPlayer(player, xCoord, yCoord, zCoord);
	}

	@Override
	public Packet getDescriptionPacket() {
		return null;
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity updatePacket) {
	}

	@Override
	public final boolean isItem() {
		return false;
	}

	public boolean isRedstonePowered() {
		return getWorldObj().isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}

	@Override
	public boolean onActivated(EntityPlayer player, ForgeDirection side) {
		if (player.getCurrentEquippedItem() != null) {
			ItemStack heldItemStack = player.getCurrentEquippedItem();
			if (heldItemStack.getItem() instanceof ITool) {
				PlayerHelper.addAdvancedChatMessage(getWorldObj(), player, "Rotation ID: '%d'.", getRotation());
				return true;
			}
		}
        return false;
	}

	public ItemStack getStackInSlot(int slot) {
		if (this instanceof IInventory && getInventory() != null) {
			ItemStack stackInSlot = getInventory()[slot];
            if (stackInSlot != null) {
                return stackInSlot;
            }
		}
        return null;
	}

	public ItemStack decrStackSize(int slotpar1, int slotpar2) {
		if (this instanceof IInventory && getInventory() != null) {
			return InventoryHelper.decrStackSize(slotpar1, slotpar2, getInventory(), (IInventory)this);
		} else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this instanceof IInventory && getInventory() != null) {
			return InventoryHelper.getStackInSlotOnClosing(slot, getInventory());
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (this instanceof IInventory && getInventory() != null) {
			InventoryHelper.setInventorySlotContents(slot, stack, getInventory(), (IInventory)this);
		}
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void openInventory() {
	}

	public void closeInventory() {
	}

    public void markDirty() {
        TileEntityHelper.markBlockForUpdate(this);
    }

	protected ItemStack[] getInventory() {
		return null;
	}

    public int getSizeInventory() {
        return this instanceof IInventory && getInventory() != null ? getInventory().length : 0;
    }

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (this instanceof IInventory && getInventory() != null) {
			InventoryHelper.writeToNBT(nbt, getInventory());
		}
        nbt.setByte("rotation", rotation);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (this instanceof IInventory && getInventory() != null) {
			InventoryHelper.readFromNBT(nbt, getInventory());
		}
		rotation = nbt.getByte("rotation");
    }

	@Override
	public boolean canBlockRotate() {
		return true;
	}

	@Override
	public byte getRotation() {
		return rotation;
	}

    @Override
    public ForgeDirection getDirection() {
        if (rotation > ForgeDirection.VALID_DIRECTIONS.length || rotation < 0) {
            return ForgeDirection.UP;
        }
        return RotationHelper.convertByteToForgeDirection(rotation);
    }

	@Override
	public final void setRotation(byte newRotation) {
		rotation = newRotation;
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

    @Override
    public boolean doesHaveGui() {
        return false;
    }

	@Override
	public Container getServerGui(InventoryPlayer inventory) {
		return null;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public GuiContainer getClientGui(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public void rotateBlock() {
        boolean doesSkipRotation = false;
        ForgeDirection direction = RotationHelper.convertIntToForgeDirection(getRotation() + 1);
        if (direction == null) {
            setRotation(RotationHelper.convertForgeDirectionToByte(ForgeDirection.DOWN));
            doesSkipRotation = true;
        } else if (direction == ForgeDirection.UNKNOWN) {
            setRotation(RotationHelper.convertForgeDirectionToByte(ForgeDirection.DOWN));
            doesSkipRotation = true;
        }
        if (!doesSkipRotation) {
            rotation++;
        }
		getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void addRotation(byte rotation) {
		if (this.rotation < rotation) {
			this.rotation += rotation;
		} else {
			this.rotation -= rotation;
		}
	}

	@Override
	public UUID getUUID() {
		return UUID.randomUUID();
	}

}
