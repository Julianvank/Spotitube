package han.jvk.spotitube.util.factory.DBConnection;

import han.jvk.spotitube.exception.UtilException;
import han.jvk.spotitube.remoteFacade.HealthResource;
import han.jvk.spotitube.util.DBPropertiesReader;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ApplicationScoped
public class DBConnectionFactory implements IDBConnectionFactory {
    private static final Logger log = Logger.getLogger(HealthResource.class.getName());

    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";

    private String url;
    private String username;
    private String password;

    public DBConnectionFactory(){
        setProperties("postgres");
    }

    public void setProperties(String name) {
        if (name == null)
            throw new UtilException("No database was given for change", HttpURLConnection.HTTP_CONFLICT);

        DBPropertiesReader properties = new DBPropertiesReader(name);

        this.url = properties.getProperty(PROPERTY_URL);
        this.password = properties.getProperty(PROPERTY_PASSWORD);
        this.username = properties.getProperty(PROPERTY_USERNAME);

        log.info("new database properties set: " + url + "username: " + username);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

