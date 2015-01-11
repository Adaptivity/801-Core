package core.api.common.mod;

/**
 * This checks if the mod has an update available for it.
 * @author Master801
 */
public interface IUpdateableMod {

	boolean checkForUpdates();

	/**
	 * This is the most current mod version have made in the game.
	 * Example: Web = Version 1.0, while the current version you have in the mod, is 0.1.
	 * You should return the mod's current version (0.1).
	 */
	String getCurrentVersion();

	/**
	 * This should only lead to a text file, that either, only has the version, or the first line is the version.
	 * @return The URL for checking the update.
	 */
	String getUpdateURL();

}
