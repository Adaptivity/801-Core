package core.api.tileentity;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Master801 on 12/6/2014 at 2:23 PM.
 * @author Master801
 */
public interface IRedstoneControl {

    byte getWeakPower(ForgeDirection side);

    byte getStrongPower(ForgeDirection side);

}
