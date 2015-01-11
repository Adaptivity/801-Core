package core.commands;

import com.google.common.base.Joiner;
import core.api.plugin.Plugin;
import core.helpers.PlayerHelper;
import core.helpers.PluginHelper;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class CommandInstalledPlugins extends CommandCoreBase {

	@Override
	public String getCommandName() {
		return "core.plugins";
	}

	@Override
	public String getCommandUsage(ICommandSender sender, EntityPlayer player) {
		return "command.installedPlugins.howto";
	}

	@Override
	public void processCommand(ICommandSender sender, EntityPlayer player, String[] args) {
		final List<String> pluginNames = new ArrayList<String>();
		for(Class clazz : PluginHelper.INSTANCE.PLUGIN_LIST) {
			if (clazz.isAnnotationPresent(Plugin.class)) {
				pluginNames.add(((Plugin)clazz.getAnnotation(Plugin.class)).name());
			}
		}
		PlayerHelper.addAdvancedChatMessage(sender.getEntityWorld(), player, "[801-Core] Installed Plugins: %s.", Joiner.on(", ").join(pluginNames));
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

}
