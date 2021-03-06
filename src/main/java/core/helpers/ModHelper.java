package core.helpers;

import com.google.common.base.Joiner;
import core.Core;
import core.api.common.mod.IMod;
import core.api.common.mod.IUpdateableMod;
import core.api.metadata.IMetadata;
import core.common.resources.CoreEnums.LoggerEnum;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author Master801
 */
public final class ModHelper {

	private static final Map<String, Class<?>> MOD_MAP = new HashMap<String, Class<?>>();
    private static final Map<String, Object> MOD_INSTANCE_MAP = new HashMap<String, Object>();
	private static final List<Class<?>> MOD_LIST = new ArrayList<Class<?>>();

	/**
	 * Make sure to fill in all variables in the metadata you are picking!
	 * @param modMetadata The ModMetadata you want to change.
	 * @param metadata The ModMetadata you want to be passed over to modMetadata.
	 */
	public static ModMetadata handshakeMetadata(ModMetadata modMetadata, final IMetadata metadata) {
        if (modMetadata == null || metadata == null) {
            return null;
        }
        if (metadata.getAuthorList() != null) {
            modMetadata.authorList = metadata.getAuthorList();
        }
        modMetadata.autogenerated = metadata.isAutoGenerated();
        if (metadata.getChildMods() != null) {
            modMetadata.childMods = metadata.getChildMods();
        }
        if (metadata.getCredits() != null) {
            modMetadata.credits = Joiner.on(", ").join(metadata.getCredits());
        } else {
            modMetadata.credits = "";
        }
        if (metadata.getDescription() != null) {
            modMetadata.description = metadata.getDescription();
        } else {
            modMetadata.description = "";
        }
        if (metadata.getLogo() != null) {
            modMetadata.logoFile = metadata.getLogo();
        } else {
            modMetadata.logoFile = "";
        }
        modMetadata.modId = metadata.getModID();
        modMetadata.name = metadata.getModName();
        if (metadata.getParentMod() != null) {
            modMetadata.parent = metadata.getParentMod();
        }
        if (metadata.getScreenShots() != null) {
            modMetadata.screenshots = metadata.getScreenShots();
        } else {
            modMetadata.screenshots = new String[0];
        }
        if (metadata.getUpdateURL() != null) {
            modMetadata.updateUrl = metadata.getUpdateURL();
        } else {
            modMetadata.updateUrl = "";
        }
        if (metadata.getURL() != null) {
            modMetadata.url = metadata.getURL();
        } else {
            modMetadata.url = "";
        }
        modMetadata.version = metadata.getModVersion();
        return modMetadata;
	}

    /**
     * Gets the mod's class, then tries to get it's modid to add to the mod map. (ModID, Class)
     */
    public static void addChildMod(Class<?> clazz) {
        if (clazz == null) {
            throw new CoreNullPointerException("Tried to add class to 801-Core's internal mod list!");
        }
        if (clazz.equals(Core.class)) {
            throw new CoreNullPointerException("The mod you tried to add to the internal mapping, is not correct! (Don't try to add the Class Core in the internal mapping!)");
        }
        if (!IMod.class.isAssignableFrom(clazz)) {
            throw new CoreNullPointerException("The Mod's class does not implement the interface IMod!");
        }
        if (clazz.getAnnotation(Mod.class) == null) {
            throw new CoreNullPointerException("The Mod's Class file does not have an Mod Annotation!");
        }
        Mod annon = clazz.getAnnotation(Mod.class);
        if (annon == null) {
            throw new CoreNullPointerException("Tried to get the Mod's modid from the Mod annotation! The annotation grabbing has failed! Did you try to add a non-mod class?");
        }
        MOD_MAP.put(annon.modid(), clazz);
        if (!MOD_LIST.contains(clazz)) {
            MOD_LIST.add(clazz);
        }
		LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Successfully added mod '%s' to 801-Core's internal mod map!", annon.modid());
        for(Field instanceField : clazz.getDeclaredFields()) {
            Instance instance = instanceField.getAnnotation(Instance.class);
            if (instance != null) {
                if (!instanceField.isAccessible()) {
                    instanceField.setAccessible(true);
                }
                Object instanceObject = ReflectionHelper.getFieldValue(instanceField, null);//FIXME We don't know if the field is static.
                if (instanceObject == null) {
                    continue;
                }
                if (!MOD_INSTANCE_MAP.containsKey(annon.modid()) && !MOD_INSTANCE_MAP.containsValue(instanceObject)) {
                    MOD_INSTANCE_MAP.put(annon.modid(), instanceObject);
                }
            }
        }
	}

	public static Class<?> getModClass(String modid) {
		if (modid == null) {
			throw new CoreNullPointerException("The ModID you specified is null!");
		}
		if (!MOD_MAP.containsKey(modid)) {
			throw new CoreNullPointerException("Could not find the Mod's Class with the specified ModID! ModID: '%s'.", modid);
		}
		Class<?> clazz = MOD_MAP.get(modid);
		if (clazz == null) {
			throw new CoreNullPointerException("The Mod's Class file you wanted to grab is null! ModID: '%s'.", modid);
		}
		return clazz;
	}

	/**
	 * Checks versions.
	 */
	public static void checkVersion(IUpdateableMod updateable, boolean override, boolean isChild) {
        if (isChild) {
            LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.INFO, "Now checking for new versions of registered 801-Core child-mods...");
        }
        LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.WARN, "Mod checking has been disabled, due to performance issues...");
        //FIXME
		if (updateable.checkForUpdates()) {
			try {
				if (updateable.getCurrentVersion().equalsIgnoreCase("@VERSION@") && !override) {
					if (updateable instanceof IMod) {
						IMod mod = (IMod)updateable;
						LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "The mod's version is not available for checking! Not bothering to check. ModID: '%s', Version: '%s'.", mod.getModID(), updateable.getCurrentVersion());
						return;
					} else {
						LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "The mod's version is not available for checking! Not bothering to check. Version: '%s'.", updateable.getCurrentVersion());
						return;
					}
				}
				if (updateable.getUpdateURL() == null) {
					LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.FATAL, "The Text File URL is null! Not checking the version...");
					return;
				}
				URL url = new URL(updateable.getUpdateURL());
				InputStream stream = url.openStream();
				if (stream == null) {
					LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.ERROR, "Could not find the text file! Does your internet happen to be down, or maybe the URL is?  URL: '%s'.", updateable.getUpdateURL());
					return;
				}
                Scanner scanner = new Scanner(stream);
                while(scanner.hasNext("#")) {
                    if (scanner.nextLine().equals("#Version")) {
                        String version = String.valueOf(scanner.next());
                        if (version.equalsIgnoreCase(updateable.getCurrentVersion())) {
                            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Your mod's version is up to date! Version: '%s'.", updateable.getCurrentVersion());
                        } else {
                            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "Your mod's version is not up to date. Or you messed up the config file somehow. New Version: '%s', Your version: '%s'.", version, updateable.getCurrentVersion());
                        }
                    }
                }
                scanner.close();
                stream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.ERROR, "The URL has failed to make a new URL?");
            } catch (IOException e) {
                e.printStackTrace();
                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.FATAL, "Failed to check the URL! (Does your internet happen to be down? Or maybe the URL is missing?) URL: '%s'.", updateable.getUpdateURL());
            }
        }
    }

    public static List<Class<?>> getAllModClasses() {
        return ModHelper.MOD_LIST;
    }

    public static Boolean isModRegistered(Class<?> mod) {
        if (mod == Core.class) {
            return new Boolean(true);
        }
        if (!IMod.class.isAssignableFrom(mod)) {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.FATAL, "The Mod is not an instance of IMod!");
            return new Boolean(false);
        }
        return ModHelper.MOD_LIST.contains(mod);
    }

    public static Object getModInstanceFromID(String modid) {
        if (modid == null) {
            return null;
        }
        return MOD_INSTANCE_MAP.get(modid);
    }

}
