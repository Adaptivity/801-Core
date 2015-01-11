package core.helpers;

import net.minecraft.util.EnumChatFormatting;

/**
 * @author Master801
 */
public final class ChatHelper {

	public static String colourString(EnumChatFormatting format, Object message) {
		return format + String.valueOf(message) + EnumChatFormatting.RESET;
	}

}
