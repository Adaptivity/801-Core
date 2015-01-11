package core.client.gui.buttons;

import core.common.resources.CoreResources;
import core.helpers.GLHelper;
import core.helpers.TextureHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Master801 on 12/12/2014 at 7:47 PM.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public class GuiButtonIcon extends GuiButtonCoreBase {

    private final TextureHelper.TextureUsageInfo textureInfo;
    private boolean renderOverButton = false;

    public GuiButtonIcon(int buttonID, int locationX, int locationY, TextureHelper.TextureUsageInfo textureInfo) {
        this(buttonID, locationX, locationY, true, textureInfo);
    }
    public GuiButtonIcon(int buttonID, int locationX, int locationY, boolean renderOverButton, TextureHelper.TextureUsageInfo textureInfo) {
        super(buttonID, locationX, locationY, null);
        this.renderOverButton = renderOverButton;
        this.textureInfo = textureInfo;
    }

    public GuiButtonIcon(int buttonID, int locationX, int locationY, int buttonWidth, int buttonHeight, boolean renderOverButton, TextureHelper.TextureUsageInfo textureInfo) {
        super(buttonID, locationX, locationY, buttonWidth, buttonHeight, null);
        this.renderOverButton = renderOverButton;
        this.textureInfo = textureInfo;
    }

    @Override
    public void drawButton(Minecraft minecraft, int par2, int par3) {
        GLHelper.glColour4f();
        TextureHelper.bindTextureFromInfo(getTextureInfo());
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(xPosition, yPosition, 0.0D, 0.0D, 0.0D);//Top-left
        tessellator.addVertexWithUV(xPosition + width, yPosition, 0.0D, 0.0D, 0.0D);//Top-right
        tessellator.addVertexWithUV(xPosition, yPosition + height, 0.0D, 0.0D, 0.0D);//Bottom-left
        tessellator.addVertexWithUV(xPosition + width, yPosition + height, 0.0D, 0.0D, 0.0D);//Bottom-right
        tessellator.draw();
        /*
        if (renderOverButton) {
            if (width == 20 && height == 20) {
                TextureHelper.bindTexture("/resources/textures/gui/menu/Blank_20x20_Button.png");
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(xPosition, yPosition, 0.0D, 20.0D, 20.0D);//Top-left
                tessellator.addVertexWithUV(xPosition + iconSize, yPosition, 0.0D, 20.0D, 20.0D);//Top-right
                tessellator.addVertexWithUV(xPosition, yPosition + iconSize, 0.0D, 20.0D, 20.0D);//Bottom-left
                tessellator.addVertexWithUV(xPosition + iconSize, yPosition, 0.0D, 20.0D, 20.0D);//Bottom-right
                tessellator.draw();
                return;
            }
            throw new NullPointerException("Custom button sizes are not yet supported! [Only 20x20 is supported!]");
        }
        */
    }

    public TextureHelper.TextureUsageInfo getTextureInfo() {
        return textureInfo;
    }

}
