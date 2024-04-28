package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.util.factory.DBConnection.IDBConnectionFactory;
import han.jvk.spotitube.persistance.IUserDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class UserDAO extends PostgresConnector implements IUserDAO {


    @Override
    public String getPasswordByUser(String username) throws DALException {
        final String querie = "SELECT password FROM users WHERE username = ?";
        String password = "";

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(querie);

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                password = rs.getString(1);
            }
            return password;

        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
    }
}
