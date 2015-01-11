package core.api.block;

import core.api.common.mod.IMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created by Master801 on 10/10/2014.
 * @author Master801
 */
public interface IRender {

    @SideOnly(Side.CLIENT)
    String getSpecialRenderID();

    IMod getMod();

}
