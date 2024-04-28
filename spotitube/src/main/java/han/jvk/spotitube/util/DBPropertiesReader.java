package han.jvk.spotitube.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBPropertiesReader {

    private static final String PROPERTIES_FILE = "/database.properties";
    private static final Properties PROPERTIES = new Properties();
    private String specificKey;

    public DBPropertiesReader(String specificKey) {
        this.specificKey = specificKey;
    }

    static {
        try (InputStream input = DBPropertiesReader.class.getResourceAsStream(PROPERTIES_FILE) ){
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key){
        String fullKey = specificKey + "." + key;

        return PROPERTIES.getProperty(fullKey);
    }
}
