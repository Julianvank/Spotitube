package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.persistance.DatabaseConnector;
import han.jvk.spotitube.persistance.IUserDAO;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class UserDAO extends DatabaseConnector implements IUserDAO {
    private static final String GET_PASSWORD_QUERY = "SELECT password FROM users WHERE username = ?";


    @Override
    public String getPasswordByUser(String username) throws DALException {
        String password = "";
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(GET_PASSWORD_QUERY);

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    password = rs.getString(1);
                }
            }

        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
        return password;
    }
}
