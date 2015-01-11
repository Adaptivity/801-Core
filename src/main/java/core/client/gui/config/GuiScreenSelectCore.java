package core.client.gui.config;

import core.helpers.LanguageHelper;
import core.helpers.PluginHelper;
import core.helpers.StringHelper;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.GuiConfigEntries.CategoryEntry;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiScreenSelectCore {

	static abstract class CategoryEntryCoreBase extends CategoryEntry {

		protected abstract GuiScreen buildChildScreen(List<IConfigElement> elements);

		public CategoryEntryCoreBase(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected final GuiScreen buildChildScreen() {
			return buildChildScreen(new ArrayList<IConfigElement>());
		}

	}

	/**
	 * A screen to select plugins.
	 * @author Master801
	 */
	public static final class GuiScreenSelectPlugin extends CategoryEntryCoreBase {
		
		public GuiScreenSelectPlugin(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}

		@Override
		protected GuiScreen buildChildScreen(List<IConfigElement> elements) {
            for(Class<?> plugin : PluginHelper.INSTANCE.PLUGIN_LIST) {
                elements.add(new PluginConfigElement(PluginHelper.INSTANCE.getPluginName(plugin)));
            }
            return new GuiConfig(owningScreen, elements, owningScreen.modID, "selectPlugin", true, true, LanguageHelper.getLocalisedString("gui.config.plugin"), "");
		}

        static final class PluginConfigElement extends ConfigElement<Boolean> {

            public PluginConfigElement(String pluginName) {
                super(new Property(pluginName, "true", Property.Type.BOOLEAN, StringHelper.advancedMessage("Enable %s Plugin", pluginName)));
            }

        }

	}

}
