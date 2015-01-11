package core.helpers;

import core.api.common.mod.IMod;
import core.common.resources.CoreEnums.LoggerEnum;
import core.common.resources.CoreResources;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Master801
 */
public final class LoggerHelper {

	/**
	 * Adds a message to the logger.
	 */
	public static void addMessageToLogger(IMod mod, LoggerEnum level, Object message) {
		if (CoreResources.getSide() == Side.CLIENT) {
            if (level == LoggerEnum.ERROR) {
                CoreResources.getLogger(mod).error(String.valueOf(message) + " Please contact Master801?");
            } else {
                CoreResources.getLogger(mod).log(level.getLevel(), String.valueOf(message));
            }
		}
	}

	/**
	 * Adds a advanced message to the logger.
	 */
	public static void addAdvancedMessageToLogger(IMod mod, LoggerEnum level, Object message, Object... advanced) {
		if (CoreResources.getSide().isClient()) {
            if (level == LoggerEnum.ERROR) {
                CoreResources.getLogger(mod).log(level.getLevel(), StringHelper.advancedMessage(String.valueOf(message) + " Please contact Master801?", advanced));
            } else {
                CoreResources.getLogger(mod).log(level.getLevel(), StringHelper.advancedMessage(String.valueOf(message), advanced));
			}
		}
	}

}
