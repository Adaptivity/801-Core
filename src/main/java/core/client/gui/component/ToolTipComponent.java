package core.client.gui.component;

import net.minecraft.client.gui.inventory.GuiContainer;
import core.api.client.gui.component.IHover;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * <b>Unfinished class.</b>
 * @author Master801
 */
public class ToolTipComponent extends GuiComponent implements IHover {

	protected int xPosition;
	protected int yPosition;
    private final List<String> list;

	public ToolTipComponent(GuiContainer parentScreen, int xPosition, int yPosition, List<String> list) {
		super(parentScreen);
		this.xPosition = xPosition;
		this.yPosition = yPosition;
        this.list = list;
	}

	@Override
	public String getComponentName() {
		return "ToolTipComponent";
	}

	@Override
	public void onBackgroundLayerDrawn(int xPos, int yPos) {
        //Do nothing.
	}

	@Override
	public void onForeLayerDrawn(int xPos, int yPos) {
        //Do nothing.
	}

    public boolean isComponentHoveredOn() {
		return false;//TODO Insert Math thingys.
	}

	@Override
	public void onHovered(int mousePoxX, int mousePoxY) {
		if (isComponentHoveredOn() && !list.isEmpty()) {
            GL11.glPushMatrix();
            drawBoxWithString(list);
            GL11.glPopMatrix();
        }
	}

    private void drawBoxWithString(List<String> list) {
        for(String string : list) {
            //TODO
        }
    }

    @Override
    public List<String> getTextBoxString() {
        return list;
    }

}
