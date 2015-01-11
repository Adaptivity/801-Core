package core.api.client.gui.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;

/**
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public interface IGuiComponent {

	String getComponentName();

	void onBackgroundLayerDrawn(int xPos, int yPos);

	void onForeLayerDrawn(int xPos, int yPos);

    GuiContainer getParentScreen();

    void setParentScreen(GuiContainer newContainer);

}
