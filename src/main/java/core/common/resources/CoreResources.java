package core.common.resources;

import core.Core;
import core.api.common.IConfigFile;
import core.api.common.mod.IMod;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.helpers.ConfigFileHelper;
import core.common.resources.CoreEnums.LoggerEnum;
import core.helpers.LoggerHelper;
import core.helpers.NetworkHelper;
import core.helpers.ReflectionHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class CoreResources {

	public static final String CORE_LIBRARY_MOD_ID = "801-Library";
	public static final String CORE_LIBRARY_NAME = CoreResources.CORE_LIBRARY_MOD_ID;
	public static final String CORE_LIBRARY_VERSION = "@MOD_VERSION@";
	public static final String CORE_LIBRARY_PROXY_SERVER = "core.proxies.CoreCommonProxy";
	public static final String CORE_LIBRARY_PROXY_CLIENT = "core.proxies.CoreClientProxy";
	private static final String GRADLE_BUILDING = "@BUILD@";
	private static final Random RANDOM = new Random();
	public static final String CORE_GUI_FACTORY = "core.client.gui.config.GuiConfigCoreFactory";
    public static final IConfigFile CORE_CONFIG_FILE = ConfigFileHelper.createNewConfigFile(Core.instance, "801-Library", " \nLibrary mod for all of Master801's mods.\n");
    private static final Map<IMod, Logger> LOGGER_MAP = new HashMap<IMod, Logger>();
    public static final CreativeTabs CORE_LIBRARY_CREATIVE_TAB = new CreativeTabs("801Core.name") {

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Items.diamond;
		}

	};

    public static Logger getLogger(IMod mod) {
		if (mod == null) {
            Logger logger = LogManager.getLogger("null");
            logger.log(Level.WARN, "**Error**");
			return logger;
		}
		Logger logger = CoreResources.LOGGER_MAP.get(mod);
		if (logger != null) {
			return logger;
		} else {
            logger = LogManager.getLogger(mod.getModID());
            LOGGER_MAP.put(mod, logger);
        }
        return logger;
    }

	/**
	 * @return The configuration directory of 801-Core.
	 */
	public static File getCoreConfigDir() {
		File configDir = Loader.instance().getConfigDir();
		File coreDir = new File(configDir, CoreResources.CORE_LIBRARY_MOD_ID);
		boolean hasBeenMade = coreDir.exists();
        if (!hasBeenMade) {
			hasBeenMade = coreDir.mkdir();
		}
        if (coreDir.exists() && hasBeenMade) {
            return coreDir;
        }
        throw new CoreNullPointerException("Config file directory does not exist, or is null!");
	}

	public static NetworkRegistry getNetworkRegistry() {
		return NetworkRegistry.INSTANCE;
	}

    @SideOnly(Side.CLIENT)
	public static Minecraft getMinecraft() {
        if (CoreResources.getSide().isClient()) {
            return Minecraft.getMinecraft();
        }
		return null;
	}

    @SideOnly(Side.CLIENT)
	public static TextureManager getTextureManager() {
        return CoreResources.getMinecraft().getTextureManager();
	}

    @SideOnly(Side.CLIENT)
	public static GameSettings getGameOptions() {
        return CoreResources.getMinecraft().gameSettings;
	}

    @SideOnly(Side.CLIENT)
	public static LanguageManager getLanguageManager() {
	    return CoreResources.getMinecraft().getLanguageManager();
    }

	public static File getMainDir() {
		return new File(".");
	}

	public static File getModsDir() {
		return ReflectionHelper.getFieldValue(ReflectionHelper.getField(Loader.class, "canonicalModsDir"), Loader.instance());
	}

	public static boolean isDevWorkspace() {
        return !CoreResources.GRADLE_BUILDING.equalsIgnoreCase("@Do_Not_Build@");
	}

	public static SimpleNetworkWrapper getCoreNetworkHandler() {
		SimpleNetworkWrapper network = NetworkHelper.makeNewSimpleNetworkWrapper("Core");
		if (network == null) {
			throw new CoreNullPointerException("Could not create new SimpleNetworkWrapper!");
		}
        LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.INFO, "Successfully created a new SimpleNetworkWrapper!");
        return network;
	}

    @SideOnly(Side.CLIENT)
	public static SimpleReloadableResourceManager getResourceManager() {
		return (SimpleReloadableResourceManager)CoreResources.getMinecraft().getResourceManager();
	}

	public static Random getRandom() {
		return RANDOM;
	}

	public static FMLDeobfuscatingRemapper getRemapper() {
		return FMLDeobfuscatingRemapper.INSTANCE;
	}

	public static LanguageRegistry getLanguageRegistry() {
		return LanguageRegistry.instance();
	}

	public static CraftingManager getCraftingManager() {
		return CraftingManager.getInstance();
	}

	public static MinecraftServer getServer() {
		return MinecraftServer.getServer();
	}

	public static Side getSide() {
		return FMLCommonHandler.instance().getEffectiveSide();
	}

	//Hidden text for me. -Master801
	/*
	 * https://google.com
	 * http://www.minecraftforge.net/forum/index.php/topic,20135.0.html
	 * http://www.minecraftforge.net/forum/index.php?topic=18044.0
	 */

}
