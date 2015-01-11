package core.helpers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Created by Master801 on 11/11/2014.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public final class GLHelper {

    /**
     * Used only to get rid of lighting glitches. (Models, gui, etc...)
     */
    public static void glColour4f() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void glColour4f(Color colour) {
        GL11.glColor4f(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getAlpha());
    }

}
