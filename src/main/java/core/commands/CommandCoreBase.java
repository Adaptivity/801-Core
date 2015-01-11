package core.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.IAdminCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.helpers.MinecraftObfuscationHelper;

/**
 * A base class file for making commands. Make sure to register the command in the server starting event!
 * @author Master801
 */
public abstract class CommandCoreBase extends CommandBase {

	@Override
	public abstract String getCommandName();

	/**
	 * Feel free to return unlocalized command text. Make sure to localize it later though!
	 */
	public abstract String getCommandUsage(ICommandSender sender, EntityPlayer player);

	public abstract void processCommand(ICommandSender sender, EntityPlayer player, String[] args);

	@Override
	public abstract int getRequiredPermissionLevel();

	@Override
	public String getCommandUsage(ICommandSender sender) {
		if (sender instanceof EntityPlayer) {
			return getCommandUsage(sender, (EntityPlayer)sender);
		} else {
			return null;
		}
	}

	@Override
	public void processCommand(ICommandSender sender, String[] astring) {
		if (sender instanceof EntityPlayer) {
			processCommand(sender, (EntityPlayer)sender, astring);
		}
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	public static IAdminCommand getAdmin() {
		final IAdminCommand admin = MinecraftObfuscationHelper.getMinecraftFieldValue(CommandBase.class, "field_71533_a", "theAdmin");
		if (admin == null) {
			throw new CoreNullPointerException("The 'theAdmin' field for CommandBase is null!");
		}
		return admin;
	}

}
