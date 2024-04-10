package han.jvk.spotitube.persistance.postgreSQL;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class PostgresConnector {
    public static Connection connect() throws SQLException {
        Connection conn = null;
        Properties properties = new Properties();
        try (InputStream input = PostgresConnector.class.getResourceAsStream("/database.properties") ){
            properties.load(input);

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = "";

            conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
