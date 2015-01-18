package core.helpers;

import core.Core;
import core.api.common.mod.IMod;
import core.api.plugin.IPlugin;
import core.api.plugin.Plugin;
import core.api.plugin.Plugin.PluginEventHandler;
import core.common.resources.CoreEnums.LoggerEnum;
import core.common.resources.CoreResources;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import cpw.mods.fml.common.event.FMLStateEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Master801
 */
public final class PluginHelper {

	public final List<Class<?>> PLUGIN_LIST = new ArrayList<Class<?>>();
	private final Map<String, Object> PLUGIN_INSTANCES_MAP = new HashMap<String, Object>();
    private final List<Class<?>> DISABLED_PLUGINS_LIST = new ArrayList<Class<?>>();

	public static final PluginHelper INSTANCE = new PluginHelper();

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
                throw new CoreNullPointerException("Failed to get the plugin's instance? Class: '%s'", pluginClass.toString());
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

    public void injectInstance(Class<?> pluginClass, Object instance) {
        if (!pluginClass.isAnnotationPresent(Plugin.class)) {
            return;
        }
        PluginHelper.INSTANCE.PLUGIN_INSTANCES_MAP.put(pluginClass.getAnnotation(Plugin.class).name(), instance);
    }

}
