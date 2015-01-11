package core.api.client.gui.component;

import java.util.List;

/**
 * @author Master801
 */
public interface IHover {

	void onHovered(int mousePoxX, int mousePoxY);

    List<String> getTextBoxString();

}
