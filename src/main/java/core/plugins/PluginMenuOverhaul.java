package core.plugins;

import core.api.common.IConfigFile;
import core.api.plugin.IPlugin;
import core.api.plugin.Plugin;
import core.api.plugin.Plugin.PluginEventHandler;
import core.api.plugin.Plugin.PluginFancyName;
import core.api.plugin.Plugin.PluginInstance;
import core.helpers.ConfigFileHelper;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author Master801
 * @author EkGame
 */
@Plugin(description = "Overhauling menus!", name = "Menu Overhaul", version = "1.0.2")
@PluginFancyName(value = EnumChatFormatting.DARK_PURPLE, doesHaveFancyName = true)
public class PluginMenuOverhaul implements IPlugin {

	@PluginInstance("Menu Overhaul")
	public static PluginMenuOverhaul instance;

    public static IConfigFile MENU_OVERHAUL_CONFIG_FILE = ConfigFileHelper.createNewConfigFile("Menu Overhaul", "Menu_Overhaul", "Makes your title-screen pretty.");

	@PluginEventHandler
	public static void preInit(FMLPreInitializationEvent event) {
        PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.loadConfigFile();
        PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.setValueFromKey("Enable Main-Menu overhaul", false);
        PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.setValueFromKey("Enable Background", true);
        PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.setValueFromKey("Enable Logo", true);
        PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.setValueFromKey("Enable Panorama", false);
        PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.setValueFromKey("Background Texture Path", "/resources/textures/gui/menu/background.png");
        PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.setValueFromKey("Logo Texture Path", "/resources/textures/gui/menu/minecraft_logo.png");
        PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.setValueFromKey("Panorama Texture Path", "/resources/textures/gui/menu/minecraft_panorama_by_liliotheone-d3d4y5a.png");
        PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.saveConfigFile();
	}

}
