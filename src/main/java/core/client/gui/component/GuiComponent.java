package core.client.gui.component;

import net.minecraft.client.gui.inventory.GuiContainer;
import core.api.client.gui.component.IGuiComponent;

/**
 * Original idea from Open Mods'.
 * @author Master801
 */
public abstract class GuiComponent implements IGuiComponent {

	private GuiContainer parentScreen = null;

	protected GuiComponent(GuiContainer parentScreen) {
		this.parentScreen = parentScreen;
	}

    @Override
	public final GuiContainer getParentScreen() {
		return parentScreen;
	}

    @Override
    public final void setParentScreen(GuiContainer newContainer) {
        parentScreen = newContainer;
    }

}
