package core.client.gui.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import core.client.gui.config.GuiScreenSelectCore.GuiScreenSelectPlugin;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.IConfigElement;

public final class GuiConfigCoreFactory implements IModGuiFactory {

	@Override
	public void initialize(Minecraft minecraft) {
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return GuiScreenCore.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}

	public static List<IConfigElement> guiElements() {
		final List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("Plugins", "plugins.select", GuiScreenSelectPlugin.class));
		return list;
	}

}
