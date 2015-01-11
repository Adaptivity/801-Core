package core.helpers;

import core.api.client.gui.IGuiHelper;
import core.api.common.mod.IMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Master801 on 10/19/2014.
 * @author Master801
 */
public final class GuiHelper {

    /**
     * This assumes you're using a tile-entity.
     */
    public static boolean openGui(IMod mod, EntityPlayer player, TileEntity tile) {
        if (mod == null || player == null || tile == null) {
            return false;
        }
        return GuiHelper.openGui(mod, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, player, (IGuiHelper)tile);
    }

    public static boolean openGui(IMod mod, World world, int xCoord, int yCoord, int zCoord, EntityPlayer player, IGuiHelper guiHelper) {
        if (mod == null || world == null || player == null || guiHelper == null) {
            return false;
        }
        if (!RegistryHelper.isGuiHandlerRegistered(mod)) {
            return false;
        }
        player.openGui(mod.getInstance(), -1, world, xCoord, yCoord, zCoord);
        return true;
    }

}
