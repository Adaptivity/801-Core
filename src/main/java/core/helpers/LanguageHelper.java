package core.helpers;

import net.minecraft.client.resources.I18n;

public final class LanguageHelper {

    public static String getLocalisedString(String unlocalisedName) {
        return I18n.format(unlocalisedName);
    }

}
