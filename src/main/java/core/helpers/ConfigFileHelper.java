package core.helpers;

import core.Core;
import core.api.common.IConfigFile;
import core.api.common.mod.IMod;
import core.common.resources.CoreEnums.LoggerEnum;
import core.common.resources.CoreResources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Master801 on 11/2/2014.
 * @author Master801
 */
public final class ConfigFileHelper {

    private static final Map<String, IConfigFile> CONFIG_FILE_MAPPING = new HashMap<String, IConfigFile>();

    public static IConfigFile createNewConfigFile(String mod, String configFileName) {
        return ConfigFileHelper.createNewConfigFile(mod, configFileName, " \n " + configFileName + " \n");
    }

    public static IConfigFile createNewConfigFile(IMod mod, String configFileName, String comment) {
        return ConfigFileHelper.createNewConfigFile(mod.getModID(), configFileName, comment);
    }

    public static IConfigFile createNewConfigFile(String mod, String configFileName, String comment) {
        if (mod == null || configFileName == null || comment == null) {
            return null;
        }
        IConfigFile configFile = CONFIG_FILE_MAPPING.get(mod);
        if (configFile == null) {
            configFile = new ConfigFile(configFileName, comment);
            ((ConfigFile) configFile).initConfigFile();
            CONFIG_FILE_MAPPING.put(mod, configFile);
        }
        return configFile;
    }

    public static void injectCustomConfigFile(String mod, IConfigFile configFile) {
        IConfigFile overwrittenConfig = CONFIG_FILE_MAPPING.get(mod);
        if (CONFIG_FILE_MAPPING.containsKey(mod) && overwrittenConfig != null) {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Injecting config file '%s' into '%s'...", configFile.getConfigFileName(), overwrittenConfig.getConfigFileName());
        }
        CONFIG_FILE_MAPPING.put(mod, configFile);
    }

    public static IConfigFile getConfigFile(IMod mod) {
        IConfigFile configFile = CONFIG_FILE_MAPPING.get(mod);
        if (CONFIG_FILE_MAPPING.containsKey(mod) && configFile != null) {
            return configFile;
        }
        return null;
    }

    private static final class ConfigFile extends Properties implements IConfigFile {

        private final String configFileName, comment;
        private final Map<Object, Object> internalConfigMapping = this;

        private ConfigFile(String configFileName, String comment) {
            if (!configFileName.contains(".cfg")) {
                this.configFileName = configFileName + ".cfg";
            } else {
                this.configFileName = configFileName;
            }
            this.comment = comment;
        }

        @Override
        public String getConfigFileName() {
            return configFileName;
        }

        @Override
        public File getFile() {
            return new File(CoreResources.getCoreConfigDir(), configFileName);
        }

        private void initConfigFile() {
            boolean hasCreated = true;
            if (!getFile().exists()) {
                try {
                    hasCreated = getFile().createNewFile();
                    LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Created new config file! Config File: '%s'", getConfigFileName());
                } catch (Exception exception) {
                    LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "Failed to create the config file! Config File: '%s', Exception: '%s'", getConfigFileName(), exception.toString());
                }
            }
            if (hasCreated) {
                loadConfigFile();
                saveConfigFile();
            } else {
                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "Failed to init the config file! Config File: '%s'", getConfigFileName());
            }
        }

        @Override
        public String getValueFromKey(String key) {
            String newKey = key.replace(' ', '_');
            if (internalConfigMapping.containsKey(newKey)) {
                return String.valueOf(internalConfigMapping.get(newKey));
            }
            return null;
        }

        @Override
        public void setValueFromKey(String key, Object value) {
            String newKey = key.replace(' ', '_');
            if (!internalConfigMapping.containsKey(newKey)) {
                internalConfigMapping.put(newKey, String.valueOf(value));
            }
        }

        @Override
        public Properties getProperties() {
            return this;
        }

        @Override
        public String getComment() {
            return comment;
        }

        @Override
        public void loadConfigFile() {
            try {
                getProperties().load(new FileInputStream(getFile()));
            } catch (Exception e) {
                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "Caught exception while trying to load the config file! Config: '%s', Exception: '%s'", toString(), e.getMessage());
            }
        }

        @Override
        public void saveConfigFile() {
            try {
                getProperties().store(new FileOutputStream(getFile()), getComment());
            } catch (Exception e) {
                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "Caught exception while trying to save the config file! Config: '%s', Exception: '%s'", toString(), e.getMessage());
            }
        }

    }

}
