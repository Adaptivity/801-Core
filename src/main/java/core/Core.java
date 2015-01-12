package core;

import core.api.common.mod.IMod;
import core.api.common.mod.IUpdateableMod;
import core.api.network.proxy.IProxy;
import core.block.test.BlockTestGuiAnimation;
import core.commands.CommandInstalledPlugins;
import core.common.FuelHandlerCoreBase;
import core.common.resources.CoreMetadata;
import core.common.resources.CoreResources;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.common.resources.CoreEnums.LoggerEnum;
import core.helpers.*;
import core.item.misc.test.ItemDebugTesting;
import core.item.tools.misc.ItemToolWrench;
import core.plugins.PluginAchievements;
import core.plugins.PluginGravel;
import core.plugins.PluginMenuOverhaul;
import core.plugins.PluginPlayerStalker;
import core.tileentity.test.TileEntityTestGuiAnimation;
import core.utilities.CoreUtilities;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * https://bitbucket.org/master801/801-core/
 * @author Master801
 */
@Mod(modid = CoreResources.CORE_LIBRARY_MOD_ID, name = CoreResources.CORE_LIBRARY_NAME, version = CoreResources.CORE_LIBRARY_VERSION, guiFactory = CoreResources.CORE_GUI_FACTORY)
public final class Core implements IMod, IUpdateableMod {

	@Instance(CoreResources.CORE_LIBRARY_MOD_ID)
	public static IMod instance;

    @SidedProxy(serverSide = CoreResources.CORE_LIBRARY_PROXY_SERVER, clientSide = CoreResources.CORE_LIBRARY_PROXY_CLIENT, modId = CoreResources.CORE_LIBRARY_MOD_ID)
    public static IProxy proxy;

	@Metadata(CoreResources.CORE_LIBRARY_MOD_ID)
	public static ModMetadata metadata;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        PluginHelper.INSTANCE.addPlugin(PluginPlayerStalker.class);
        PluginHelper.INSTANCE.addPlugin(PluginGravel.class);
        PluginHelper.INSTANCE.addPlugin(PluginAchievements.class);
        PluginHelper.INSTANCE.addPlugin(PluginMenuOverhaul.class);
        CoreResources.CORE_CONFIG_FILE.loadConfigFile();
        for(Class pluginClass : PluginHelper.INSTANCE.PLUGIN_LIST) {
            CoreResources.CORE_CONFIG_FILE.setValueFromKey(StringHelper.advancedMessage("Enable %s Plugin", PluginHelper.INSTANCE.getPluginName(pluginClass)), !PluginHelper.INSTANCE.isPluginDisabled(pluginClass));
        }
        CoreResources.CORE_CONFIG_FILE.saveConfigFile();
		RegistryHelper.registerItem(new ItemDebugTesting(), "debugTesting");
		RegistryHelper.registerItem(new ItemToolWrench(), "toolWrench");
        RegistryHelper.registerTileEntity(TileEntityTestGuiAnimation.class, "801-Core.TileEntity.Test.Gui_Animation");
        RegistryHelper.registerBlock(new BlockTestGuiAnimation(), "801-Core.Blocks.Test.Gui_Animation");
		CoreResources.getCoreNetworkHandler();
		ModHelper.handshakeMetadata(metadata, new CoreMetadata());
		PluginHelper.INSTANCE.startAllPluginEvents(event);
		PluginPlayerStalker.addPlayerToBlacklist(UUID.fromString("99c5c737-f1ab-45a4-9757-1e80425643f2"));
        ProxyHelper.addProxyToMapping(Core.proxy);
    }

	@EventHandler
	public static void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new CoreUtilities());
		FMLCommonHandler.instance().bus().register(new CoreUtilities());
		GameRegistry.registerFuelHandler(FuelHandlerCoreBase.INSTANCE);
		ModHelper.checkVersion((IUpdateableMod)Core.instance, true, false);
		boolean canCheck = true;
		if (ModHelper.getAllModClasses().isEmpty()) {
			LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.WARN, "Found no child-mods!");
			canCheck = false;
		}
		while(canCheck) {
			for(Class<?> modClass: ModHelper.getAllModClasses()) {
				for(Field field : modClass.getDeclaredFields()) {
                    SidedProxy proxyAnnon = field.getAnnotation(SidedProxy.class);
                    if (proxyAnnon != null && CoreResources.isDevWorkspace()) {//Ensures that only the dev gets the error.
                        field.setAccessible(true);
                        IProxy proxy = ReflectionHelper.getFieldValue(field, ModHelper.getModInstanceFromID(ExternalModsHelper.getModIDFromClass(modClass)));
                        if (proxy != null) {
                            if (CoreResources.getSide().isClient() && proxy.getSide().isServer()) {
                                throw new CoreNullPointerException("The server IProxy was attempting to load on the client side!");
                            } else if (CoreResources.getSide().isServer() && proxy.getSide().isClient()) {
                                throw new CoreNullPointerException("The client IProxy was attempting to load on the server side!");
                            }
                        }
                    }
                }
                if (!IUpdateableMod.class.isAssignableFrom(modClass)) {
					LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.WARN, "Either found no child-mods, or some of the child-mods are not an instance of IUpdateableMod!");
					canCheck = false;
					continue;
				}
				for(Field field : modClass.getDeclaredFields()) {
					if (field.getAnnotation(Instance.class) != null) {
						ModHelper.checkVersion((IUpdateableMod) ReflectionHelper.getFieldValue(field, null), false, true);
						canCheck = false;
					}
				}
			}
		}
		Core.proxy.registerBlockRenderers();
        Core.proxy.registerTileEntityRenderers();
        Core.proxy.registerItemRenderers();
		PluginHelper.INSTANCE.startAllPluginEvents(event);
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		PluginHelper.INSTANCE.startAllPluginEvents(event);
	}

	@EventHandler
	public static void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandInstalledPlugins());
		PluginHelper.INSTANCE.startAllPluginEvents(event);
	}

	@Override
	public String getModID() {
		return CoreResources.CORE_LIBRARY_MOD_ID;
	}

	@Override
	public boolean checkForUpdates() {
		return true;
	}

	@Override
	public String getCurrentVersion() {
		return CoreResources.CORE_LIBRARY_VERSION;
	}

	@Override
	public String getUpdateURL() {
		return "https://dl.dropboxusercontent.com/u/90842632/Mods/801-Core/Log.info";
	}

	@Override
	public IMod getInstance() {
		return Core.instance;
	}

}
