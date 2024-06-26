package han.jvk.spotitube.util;


import han.jvk.spotitube.exception.UtilException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Properties;

public class DBPropertiesReader {

    private static final String PROPERTIES_FILE = "/database.properties";
    private static final Properties PROPERTIES = new Properties();
    private final String specificKey;

    public DBPropertiesReader(String specificKey) {
        this.specificKey = specificKey;
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = DBPropertiesReader.class.getResourceAsStream(PROPERTIES_FILE) ){
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new UtilException("Database properties could not be read.", e, HttpURLConnection.HTTP_CONFLICT);
        }
    }

    public String getProperty(String key){
        String fullKey = specificKey + "." + key;
        return PROPERTIES.getProperty(fullKey);
    }
}
