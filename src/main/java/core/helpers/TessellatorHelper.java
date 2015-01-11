package core.helpers;

import core.common.resources.CoreResources;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

/**
 * Created by Master801 on 12/7/2014 at 3:16 PM.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public final class TessellatorHelper {

    /**
     * Inspired from friend.
     */
    public static void drawTexturedModalRectangleFromSizes(Gui gui, int xSize, int ySize, int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_) {
        float zLevel = MinecraftObfuscationHelper.getMinecraftFieldValue(Gui.class, gui, "field_73735_i", "zLevel");
        if (xSize == 0 || ySize == 0) {
            return;
        }
        float f = 1.0F / xSize;
        float f1 = 1.0F / ySize;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(p_73729_1_, p_73729_2_ + p_73729_6_, zLevel, p_73729_3_ * f, (p_73729_4_ + p_73729_6_) * f1);
        tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, p_73729_2_ + p_73729_6_, zLevel, (p_73729_3_ + p_73729_5_) * f, (p_73729_4_ + p_73729_6_) * f1);
        tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, p_73729_2_, zLevel, (p_73729_3_ + p_73729_5_) * f, p_73729_4_ * f1);
        tessellator.addVertexWithUV(p_73729_1_, p_73729_2_, zLevel, p_73729_3_ * f, p_73729_4_ * f1);
        tessellator.draw();
    }

    public static void drawTextureToEntireScreen(int textureX, int textureY) {
    	Tessellator tessellator = new Tessellator();
    	tessellator.startDrawingQuads();
        tessellator.addVertex(0.0D, 0.0D, 0.0D);//Top-left
        tessellator.addVertex(textureX, 0.0D, 0.0D);//Top-right
        tessellator.addVertex(0.0D, textureY, 0.0D);//Bottom-left
        tessellator.addVertex(textureX, textureY, 0.0D);//Bottom-right
    	tessellator.draw();
    }

}
