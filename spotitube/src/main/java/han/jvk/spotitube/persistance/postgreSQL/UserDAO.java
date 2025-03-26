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


    @Override
    public String getPasswordByUser(String username) throws DALException {
        final String query = "SELECT password FROM users WHERE username = ?";
        String password = "";

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                password = rs.getString(1);
            }

        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
        return password;
    }
}
