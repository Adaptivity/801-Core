package core.client.gui.config;

import net.minecraft.client.gui.GuiScreen;
import core.client.gui.config.GuiConfigCoreFactory;
import core.common.resources.CoreResources;
import cpw.mods.fml.client.config.GuiConfig;

/**
 * Dummy class.
 * @author Master801
 */
public class GuiScreenCore extends GuiConfig {

	protected static boolean requireWorldRestart = true;
	protected static boolean requireClientRestart = true;

	public GuiScreenCore(GuiScreen parentScreen) {
		super(parentScreen, GuiConfigCoreFactory.guiElements(), CoreResources.CORE_MODID, null, requireWorldRestart, requireClientRestart, "Core");
	}

}
