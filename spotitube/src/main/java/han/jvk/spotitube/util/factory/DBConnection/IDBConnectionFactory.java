package han.jvk.spotitube.util.factory.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;


public interface IDBConnectionFactory {

    void setProperties(String name);
    Connection getConnection() throws SQLException;
}
