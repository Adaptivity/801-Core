package core.api.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

/**
 * @author Master801
 */
public interface IGuiHelper {

	Container getServerGui(InventoryPlayer inventory);

    @SideOnly(Side.CLIENT)
	GuiContainer getClientGui(InventoryPlayer inventory);

	/**
	 * @return true if it is an item, else return false if it is not.
	 */
	boolean isItem();

	/**
	 * To check if the tile-entity/item does in-fact, use a gui.
	 */
	boolean doesHaveGui();

}
