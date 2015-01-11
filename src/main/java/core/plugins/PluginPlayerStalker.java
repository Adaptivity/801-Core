package core.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.helpers.LanguageHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import core.Core;
import core.api.plugin.Plugin;
import core.api.plugin.Plugin.PluginEventHandler;
import core.api.plugin.Plugin.PluginFancyName;
import core.api.plugin.Plugin.PluginInstance;
import core.common.resources.CoreEnums.LoggerEnum;
import core.helpers.LoggerHelper;
import core.helpers.PlayerHelper;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

/**
 * Stalks most players currently in the game. Excluding the black listed ones of course.
 * @author Master801
 */
@Plugin(description = "Stalks most players in the current game.", name = "Player Stalker", version = "0.1.1")
@PluginFancyName(value = EnumChatFormatting.RED, doesHaveFancyName = true)
public class PluginPlayerStalker {

    private final CommandMPStalkPlayer COMMAND_INSTANCE = new CommandMPStalkPlayer();

	@PluginInstance("Player Stalker")
	public static PluginPlayerStalker instance;

	@PluginEventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandMPStalkPlayer());
	}

    @Deprecated
    public static void addPlayerToBlacklist(String username) {
        PluginPlayerStalker.instance.COMMAND_INSTANCE.USERNAME_BLACK_LIST.add(username);
    }

    public static void addPlayerToBlacklist(UUID player) {
        PluginPlayerStalker.instance.COMMAND_INSTANCE.UUID_BLACK_LIST.add(player);
    }

	/**
	 * Command based stalking.
	 * @author Master801
	 */
	class CommandMPStalkPlayer extends CommandBase {

        @Deprecated
		private final List<String> USERNAME_BLACK_LIST = new ArrayList<String>();
		private final List<UUID> UUID_BLACK_LIST = new ArrayList<UUID>();

		@Override
		public String getCommandName() {
			return "stalk";
		}

		@Override
		public int getRequiredPermissionLevel() {
			return 3;
		}

		@Override
		public boolean canCommandSenderUseCommand(ICommandSender sender) {
			return sender.canCommandSenderUseCommand(getRequiredPermissionLevel(), getCommandName());
		}

		@Override
		public String getCommandUsage(ICommandSender sender) {
			return LanguageHelper.getLocalisedString("plugins.command.stalker.howto");
		}

		@Override
		public void processCommand(ICommandSender sender, String[] strings) {
			EntityPlayerMP player = null;
			if (strings.length <= 0) {
				throw new WrongUsageException(LanguageHelper.getLocalisedString("plugins.command.stalker.howto"));
			}
			if (sender != null && sender instanceof EntityPlayer) {
				player = (EntityPlayerMP)sender;
				if (strings.length != 2 && strings.length != 4) {
					player = getCommandSenderAsPlayer(sender);
				} else {
					player = getPlayer(sender, strings[0]);
					if (player == null) {
						throw new PlayerNotFoundException();
					}
				}
				if (strings.length != 3 && strings.length != 4) {
					if (strings.length == 1 || strings.length == 2) {
						final EntityPlayerMP entityplayermp1 = getPlayer(sender, strings[strings.length - 1]);
						if (entityplayermp1 == null) {
							throw new PlayerNotFoundException();
						}
						if (entityplayermp1.worldObj != player.worldObj) {
							return;
						}
						player.mountEntity(null);
                        if (USERNAME_BLACK_LIST.contains(player.getGameProfile().getName())) {
                            PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] That player has asked to not be stalked!");
                            PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] Try stalking another player!");
                        } else {
                            PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] xPos: '%f', yPos: '%f', zPos: '%f'", entityplayermp1.posX, entityplayermp1.posY, entityplayermp1.posZ);
                        }
                        if (UUID_BLACK_LIST.contains(player.getGameProfile().getId())) {
                            PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] That player has asked to not be stalked!");
                            PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] Try stalking another player!");
                        } else {
                            PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] xPos: '%f', yPos: '%f', zPos: '%f'", entityplayermp1.posX, entityplayermp1.posY, entityplayermp1.posZ);
                        }
					}
				} else if (player.worldObj != null) {
					int i = strings.length - 3;
					double d0 = func_110666_a(sender, player.posX, strings[i++]);
					double d1 = func_110665_a(sender, player.posY, strings[i++], 0, 0);
					double d2 = func_110666_a(sender, player.posZ, strings[i++]);
                    if (USERNAME_BLACK_LIST.contains(player.getGameProfile().getName())) {
                        PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] That player has asked to not be stalked!");
                        PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] Try stalking another player!");
                    } else {
                        PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] xPos: '%f', yPos: '%f', zPos: '%f'", d0, d1, d2);
                    }
                    if (UUID_BLACK_LIST.contains(player.getGameProfile().getId())) {
                        PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] That player has asked to not be stalked!");
                        PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] Try stalking another player!");
                    } else {
                        PlayerHelper.addAdvancedChatMessage(player.getEntityWorld(), player, "[Player-Stalker] xPos: '%f', yPos: '%f', zPos: '%f'", d0, d1, d2);
                    }
				}
			}
		}

		@Override
		public List addTabCompletionOptions(ICommandSender sender, String[] array) {
			return array.length != 1 && array.length != 2 ? null : getListOfStringsMatchingLastWord(array, MinecraftServer.getServer().getAllUsernames());
		}

		@Override
		public int compareTo(Object object) {
			return 0;
		}

	}

}
