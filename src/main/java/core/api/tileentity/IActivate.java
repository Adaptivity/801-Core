package core.api.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Master801 on 10/8/2014.
 * @author Master801
 */
public interface IActivate {

    boolean onActivated(EntityPlayer player, ForgeDirection side);

}
