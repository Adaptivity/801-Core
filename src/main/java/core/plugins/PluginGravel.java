package core.plugins;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumChatFormatting;
import core.api.plugin.Plugin;
import core.api.plugin.Plugin.PluginEventHandler;
import core.api.plugin.Plugin.PluginFancyName;
import core.api.plugin.Plugin.PluginInstance;
import cpw.mods.fml.common.event.FMLInitializationEvent;

/**
 * @author Master801
 */
@Plugin(description = "Adds the old Gravel block texture back in.", name = "Gravel", version = "1.2")
@PluginFancyName(value = EnumChatFormatting.DARK_AQUA, doesHaveFancyName = true)
public class PluginGravel {

	@PluginInstance("Gravel")
	public static PluginGravel instance;

	@PluginEventHandler
	public void initStage(FMLInitializationEvent event) {
		Blocks.gravel.setBlockTextureName("core:old_gravel");
	}

}
