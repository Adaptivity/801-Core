package core.utilities;

import core.Core;
import core.api.common.IConfigFile;
import core.client.gui.screen.GuiScreenMainMenu;
import core.common.resources.CoreEnums.LoggerEnum;
import core.common.resources.CoreResources;
import core.helpers.ConfigFileHelper;
import core.helpers.LoggerHelper;
import core.helpers.PlayerHelper;
import core.plugins.PluginMenuOverhaul;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

/**
 * Event handler for 801-Core.
 * @author Master801
 */
public final class CoreUtilities {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderPlayerSpecials(RenderPlayerEvent.Specials.Pre event) {
		if (event.entityPlayer instanceof AbstractClientPlayer) {
			AbstractClientPlayer playerClient = (AbstractClientPlayer)event.entityPlayer;
			PlayerHelper.renderSkins(playerClient);
		} else {
			LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.WARN, "EntityPlayer is not an instance of AbstractClientPlayer. Not rendering Specials...");
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void injectNewMainMenu(GuiOpenEvent event) {
        if (event.gui instanceof GuiMainMenu) {
            if (Boolean.valueOf(CoreResources.CORE_CONFIG_FILE.getValueFromKey("Enable Menu Overhaul Plugin")) && Boolean.valueOf(PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.getValueFromKey("Enable Main-Menu overhaul"))) {
                event.gui = new GuiScreenMainMenu();
            }
        }
	}

}
