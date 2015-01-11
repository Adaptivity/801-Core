package core.client.gui.buttons;

import core.helpers.RandomHelper;
import net.minecraft.client.gui.GuiButton;

/**
 * Created by Master801 on 12/13/2014 at 3:16 PM.
 * @author Master801
 */
public class GuiButtonCoreBase extends GuiButton {

    public GuiButtonCoreBase(int buttonID, int xPosition, int yPosition, String buttonText) {
        super(buttonID, xPosition, yPosition, buttonText);
    }

    public GuiButtonCoreBase(int buttonID, int xPosition, int yPosition, int xSize, int ySize, String buttonText) {
        super(buttonID, xPosition, yPosition, xSize, ySize, buttonText);
    }

    public final ButtonStates getButtonState() {
        switch(getHoverState(func_146115_a())) {
            case 0:
                return ButtonStates.DISABLED;
            case 1:
                return ButtonStates.NOT_HOVERED;
            case 2:
                return ButtonStates.HOVERED;
            default:
                return ButtonStates.NOT_HOVERED;
        }
    }

    public static enum ButtonStates {

        DISABLED(0),

        HOVERED(1),

        NOT_HOVERED(2);

        private final int state;

        private ButtonStates(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }

    }

}
