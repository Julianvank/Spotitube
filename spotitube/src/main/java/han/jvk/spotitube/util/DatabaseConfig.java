package han.jvk.spotitube.util;

import jakarta.enterprise.inject.Default;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Default
public class DatabaseConfig {

    public Properties readPropertiesFile(String filename) throws IOException {
        InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(filename);
        Properties propertie = new Properties();

        propertie.load(input);
        return propertie;
    }
}
