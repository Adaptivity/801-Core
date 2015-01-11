package core.plugins;

import net.minecraft.util.EnumChatFormatting;
import core.api.plugin.Plugin;
import core.api.plugin.Plugin.PluginEventHandler;
import core.api.plugin.Plugin.PluginFancyName;
import core.api.plugin.Plugin.PluginInstance;
import cpw.mods.fml.common.event.FMLInitializationEvent;

/**
 * Based off of SonicJumper's old, 'MoarAchievements' mod.
 * By the way, <b><u>I</u></b> was the one that gave him back his Source-Code. ;)
 * You can thank me later in person if you ever meet me.
 * @author Master801
 */
@Plugin(description = "Moar Achievements from SonicJumper!", name = "Moar Achievements", version = "0.1.0")
@PluginFancyName(value = EnumChatFormatting.DARK_GREEN, doesHaveFancyName = true)
public class PluginAchievements {

	@PluginInstance("Moar Achievements")
	public static PluginAchievements instance;

	@PluginEventHandler
	public static void init(FMLInitializationEvent event) {
	}

}
