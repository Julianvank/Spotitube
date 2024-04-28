package han.jvk.spotitube.util.factory.DBConnection;

import han.jvk.spotitube.util.DBPropertiesReader;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@ApplicationScoped
public class DBConnectionFactory implements IDBConnectionFactory {
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";

    private String url;
    private String username;
    private String password;

    public DBConnectionFactory(){
        setProperties("postgres");
    }

    public void setProperties(String name) {
        if (name == null) {
            //TODO throw exception
        }

        DBPropertiesReader properties = new DBPropertiesReader(name);

        this.url = properties.getProperty(PROPERTY_URL);
        String driverClassName = properties.getProperty(PROPERTY_DRIVER);
        this.password = properties.getProperty(PROPERTY_PASSWORD);
        this.username = properties.getProperty(PROPERTY_USERNAME);

        System.out.println(name + "url is: " + url);
    }

    /**
     * Returns a connection to the database. Package private so that it can be used inside the DAO
     * package only.
     *
     * @return A connection to the database.
     * @throws SQLException If acquiring the connection fails.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

