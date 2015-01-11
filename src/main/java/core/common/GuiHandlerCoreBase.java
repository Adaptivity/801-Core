package core.common;

import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import core.api.client.gui.IGuiHelper;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Master801
 */
public final class GuiHandlerCoreBase implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int xCoord, int yCoord, int zCoord) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        IGuiHelper guiHelper = null;
        if (tile instanceof IGuiHelper) {
            guiHelper = (IGuiHelper)tile;
            if (guiHelper.doesHaveGui() && !guiHelper.isItem()) {
                return guiHelper.getServerGui(player.inventory);
            }
        }
        if (player.getCurrentEquippedItem() != null) {
            Item heldItem = player.getCurrentEquippedItem().getItem();
            if (heldItem instanceof IGuiHelper) {
                guiHelper = (IGuiHelper)heldItem;
                if (guiHelper.doesHaveGui() && guiHelper.isItem()) {
                    return guiHelper.getServerGui(player.inventory);
                }
            }
        }
        return null;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int xCoord, int yCoord, int zCoord) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        IGuiHelper guiHelper = null;
        if (tile instanceof IGuiHelper) {
            guiHelper = (IGuiHelper)tile;
            if (guiHelper.doesHaveGui() && !guiHelper.isItem()) {
                return guiHelper.getClientGui(player.inventory);
            }
        }
        if (player.getCurrentEquippedItem() != null) {
            Item heldItem = player.getCurrentEquippedItem().getItem();
            if (heldItem instanceof IGuiHelper) {
                guiHelper = (IGuiHelper)heldItem;
                if (guiHelper.doesHaveGui() && guiHelper.isItem()) {
                    return guiHelper.getClientGui(player.inventory);
                }
            }
        }
        return null;
	}

}
