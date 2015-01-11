package core.helpers;

import core.Core;
import core.common.resources.CoreEnums;

/**
 * @author Master801
 */
public final class StringHelper {

	public static String advancedMessage(String message, Object... advanced) {
		return String.format(message, advanced);
	}

	public static char getQuotationMarkCharacter() {
		return '"';
	}

	public static String convertCharacterToString(char character) {
		return String.valueOf(character);
	}

    public static String makeFirstCharacterAsUppercase(String string) {
        String firstString = StringHelper.convertCharacterToString(string.charAt(0));//FIXME Not really sure if this is zero or one.
        return string.replaceFirst(firstString, firstString.toUpperCase());
    }

    public static String getWordAfterFirstCharacter(String sentence, char firstCharacter) {
        if (sentence == null) {
            return "Sentence is null.";
        }
        boolean foundFirst = false;
        boolean foundSecond = false;
        int firstIndex = -1;
        int secondIndex = -1;
        for(int i = 0; i < sentence.toCharArray().length; i++) {
            char currentCharacter = sentence.toCharArray()[i];
            if (currentCharacter == firstCharacter) {
                foundFirst = true;
                firstIndex = i;
                continue;
            }
            if (foundFirst) {
                for(char currentCharacter2 : sentence.toCharArray()) {
                    if (currentCharacter2 == firstCharacter && i != firstIndex) {
                        foundSecond = true;
                        secondIndex = i;
                    } else {
                        LoggerHelper.addAdvancedMessageToLogger(Core.instance, CoreEnums.LoggerEnum.WARN, "Failed to get the second character in a string? String: '%s'", sentence);
                    }
                }
            }
        }
        if (foundFirst && foundSecond && firstIndex != -1 && secondIndex != -1) {
            return sentence.substring(firstIndex, secondIndex);
        }
        return null;
    }

    public static String removeWordFromString(String string, String word) {
        return string.replace(word, "");
    }

    public static boolean containsCharacter(String string, char character) {
        boolean containsCharacter = false;
        for(char character_ : string.toCharArray()) {
            if (character_ == character) {
                containsCharacter = true;
            }
        }
        return containsCharacter;
    }

}
