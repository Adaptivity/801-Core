package core.helpers;

import core.Core;
import core.api.common.mod.IMod;
import core.api.plugin.IPlugin;
import core.api.plugin.Plugin;
import core.api.plugin.Plugin.PluginEventHandler;
import core.api.plugin.Plugin.PluginInstance;
import core.api.plugin.Plugin.PluginVersionChecker;
import core.common.resources.CoreEnums.LoggerEnum;
import core.common.resources.CoreResources;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLStateEvent;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Master801
 */
public final class PluginHelper {

	public final List<Class<?>> PLUGIN_LIST = new ArrayList<Class<?>>();
	private final Map<String, Object> PLUGIN_INSTANCES_MAP = new HashMap<String, Object>();
    private final List<Class<?>> DISABLED_PLUGINS_LIST = new ArrayList<Class<?>>();

	public static final PluginHelper INSTANCE = new PluginHelper();

	/**
	 * For adding new Plugins with the Plugin annotation.
	 * Make sure to load the plugins first before anything else happens, or else crashing will commence.
	 */
	public void addPlugin(Class<?> pluginClass) {
		Plugin plugin = null;
		PluginEventHandler eventHandler = null;
		PluginVersionChecker checker = null;
		if (pluginClass.isAnnotationPresent(Plugin.class)) {
			plugin = pluginClass.getAnnotation(Plugin.class);
            PLUGIN_LIST.add(pluginClass);
		} else {
            return;
        }
        String pluginName = plugin.name();
		if (pluginClass.isAnnotationPresent(PluginVersionChecker.class)) {
			checker = pluginClass.getAnnotation(PluginVersionChecker.class);
			if (checker.doesCheckForVersion()) {
				if (checker.value() != null) {
					InputStream stream = InputStreamHelper.getStreamFromURL(checker.value());
					Scanner scanner = new Scanner(stream);
					String checkedVersion = null;
					do {
						if (scanner.nextLine().equalsIgnoreCase("#VERSION")) {
							checkedVersion = scanner.nextLine();
						}
					} while(scanner.hasNextLine());
					scanner.close();
					InputStreamHelper.closeInputStream(stream);
                    IMod modInstance = null;
                    for(Field field : plugin.owner().getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.getAnnotation(Instance.class) != null) {
                            try {
                                modInstance = (IMod)field.get(null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (!plugin.version().toLowerCase().equalsIgnoreCase(checkedVersion)) {
                        LoggerHelper.addAdvancedMessageToLogger(modInstance, LoggerEnum.INFO, "A new update is available for Plugin '%s'! New Version: '%s', Current Version: '%s'.", pluginName, checkedVersion, plugin.version());
                    } else {
                        LoggerHelper.addAdvancedMessageToLogger(modInstance, LoggerEnum.INFO, "The update URL for Plugin '%s' is either, null, or not specified. URL: '%s'", pluginName);
                    }
				}
			}
		}
		for(Field field : pluginClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(PluginInstance.class)) {
				Object instance = null;
				try {
                    instance = pluginClass.newInstance();
				} catch(Exception e) {
					e.printStackTrace();
                    throw new CoreNullPointerException("Failed to institate the Plugin! Plugin: '%s'.", pluginName);
				}
				if (instance == null) {
					throw new CoreNullPointerException("The Plugin's 'instance' is null! Plugin: '%s'.", pluginName);
				}
                PLUGIN_INSTANCES_MAP.put(pluginName, instance);
				if (field.getAnnotation(PluginInstance.class) != null) {
                    if (!ReflectionHelper.isModifierFinal(field)) {
                        ReflectionHelper.setFieldValue(field, instance, instance);
                    } else {
                        throw new CoreNullPointerException("The Plugin's instance field is final! Plugin: '%s'", pluginName);
                    }
                    LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Successfully set %s's instance field!", pluginName);
				}
			}
		}
	}

	public void startAllPluginEvents(FMLStateEvent event) {
		if (PLUGIN_LIST.isEmpty()) {
			LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.WARN, "Found no available plugins... Not running events...");
			return;
		}
		for(Class<?> pluginClass : PLUGIN_LIST) {
            if (PluginHelper.INSTANCE.isPluginDisabled(pluginClass)) {
                continue;
            }
            String pluginName = PluginHelper.INSTANCE.getPluginName(pluginClass);
            if (pluginName == null) {
                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.FATAL, "Failed to get the plugin's name!");
                continue;
            }
			for(Method method : pluginClass.getDeclaredMethods()) {
                boolean accessible = method.isAccessible();
                if (!accessible) {
                    method.setAccessible(true);
                }
				for(Class<?> clazz : method.getParameterTypes()) {
                    if (!method.isAnnotationPresent(PluginEventHandler.class)) {
                        if (CoreResources.isDevWorkspace()) {
                            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "The annotation 'PluginEventHandler' is not implemented in any of the methods! Not running code for errors will appear... Plugin: '%s'.", pluginName);
                        }
                        continue;
                    }
					if (clazz == event.getClass()) {
                        Object pluginInstance = PluginHelper.INSTANCE.getPluginInstanceFromName(pluginName);
						if (pluginInstance == null) {
							throw new CoreNullPointerException("The Plugin's instance could not be found!");
						}
                        ReflectionHelper.invokeVoidMethod(method, pluginInstance, event);
                        LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Successfully invoked the method '%s'! Plugin Name: '%s'.", method.getName(), pluginName);
					}
				}
                method.setAccessible(accessible);
			}
		}
	}

    public Object getPluginInstanceFromName(String name) {
        if (name == null) {
            return null;
        }
        return PluginHelper.INSTANCE.PLUGIN_INSTANCES_MAP.get(name);
    }

    public IMod createFakeMod(final IPlugin plugin) {
        return new IMod() {

            @Override
            public String getModID() {
                return getPluginName(plugin.getClass());
            }

            @Override
            public IMod getInstance() {
                return this;
            }

        };
    }

    public boolean isPluginLoaded(String pluginName) {
        return PluginHelper.INSTANCE.PLUGIN_INSTANCES_MAP.get(pluginName) != null;
    }

    public boolean isPluginLoaded(Class<?> pluginClass) {
        return PluginHelper.INSTANCE.isPluginLoaded(PluginHelper.INSTANCE.getPluginName(pluginClass));
    }

    public String getPluginName(Class<?> pluginClass) {
        Plugin plugin = pluginClass.getAnnotation(Plugin.class);
        if (plugin != null) {
            return plugin.name();
        }
        return null;
    }

    public Object getPluginInstance(Class<?> pluginClass) {
        Object instance = null;
        for(Field field : ReflectionHelper.getFields(ReflectionHelper.getStringFromClass(pluginClass))) {
            boolean isAccessible = field.isAccessible();
            if (!isAccessible) {
                field.setAccessible(true);
            }
            Plugin plugin = pluginClass.getAnnotation(Plugin.class);
            if (plugin == null) {
                return null;
            }
            instance = ReflectionHelper.getFieldValue(field, PluginHelper.INSTANCE.getPluginInstanceFromName(plugin.name()));
            field.setAccessible(isAccessible);
            if (instance == null) {
                throw new CoreNullPointerException("Apparently I failed to get the plugin's instance? Class: '%s'", pluginClass.toString());
            }
        }
        return instance;
    }

    public boolean isValidPluginClass(Class<?> clazz) {
        return clazz.isAnnotationPresent(Plugin.class);
    }

    public boolean isPluginDisabled(Class<?> pluginClass) {
        return PluginHelper.INSTANCE.DISABLED_PLUGINS_LIST.contains(pluginClass);
    }

    public void disablePlugin(Class<?> pluginClass) {
        if (PluginHelper.INSTANCE.isValidPluginClass(pluginClass)) {
            if (!PluginHelper.INSTANCE.DISABLED_PLUGINS_LIST.contains(pluginClass)) {
                PluginHelper.INSTANCE.DISABLED_PLUGINS_LIST.add(pluginClass);
            }
        }
    }

}
