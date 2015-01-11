package core.tileentity.test;

import core.Core;
import core.client.gui.test.GuiTestGuiAnimation;
import core.common.inventory.container.test.ContainerTestGuiAnimation;
import core.helpers.GuiHelper;
import core.helpers.PlayerHelper;
import core.tileentity.TileEntityCoreBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Master801 on 10/13/2014.
 * @author Master801
 */
public class TileEntityTestGuiAnimation extends TileEntityCoreBase implements IInventory {

    /**
     * Make sure to open the gui if you have it implemented into your tile-entity!
     */
    @Override
    public boolean onActivated(EntityPlayer player, ForgeDirection side) {
        if (PlayerHelper.isPlayerNotSneaking(player)) {
            return GuiHelper.openGui(Core.instance, player, this);
        }
        return false;
    }

    @Override
    public Container getServerGui(InventoryPlayer inventory) {
        return new ContainerTestGuiAnimation(inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiContainer getClientGui(InventoryPlayer inventory) {
        return new GuiTestGuiAnimation(new ContainerTestGuiAnimation(inventory, this));
    }

    @Override
    public boolean doesHaveGui() {
        return true;
    }

    @Override
    public String getInventoryName() {
        return "core.test_gui_animation";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }

    @Override
    protected ItemStack[] getInventory() {
        return new ItemStack[0];
    }

}
