package core.api.common;

import java.io.File;
import java.util.Properties;

/**
 * Created by Master801 on 11/2/2014.
 * @author Master801
 */
public interface IConfigFile {

    String getConfigFileName();

    File getFile();

    String getValueFromKey(String key);

    void setValueFromKey(String key, Object value);

    Properties getProperties();

    String getComment();

    void loadConfigFile();

    void saveConfigFile();

}
