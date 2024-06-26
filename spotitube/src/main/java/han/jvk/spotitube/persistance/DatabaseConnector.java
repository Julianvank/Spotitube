package han.jvk.spotitube.persistance;

import han.jvk.spotitube.util.factory.DBConnection.IDBConnectionFactory;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseConnector {
    private IDBConnectionFactory connector;

    @Inject
    public void setConnector(IDBConnectionFactory connector){
        this.connector = connector;
    }

    protected Connection connect() throws SQLException {
        return connector.getConnection();
    }
}
