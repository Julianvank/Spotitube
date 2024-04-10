package han.jvk.spotitube.persistance.local;

import han.jvk.spotitube.persistance.IUserDAO;
import han.jvk.spotitube.persistance.postgreSQL.PostgresConnector;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class UserDAO extends PostgresConnector implements IUserDAO {
    @Override
    public String getPasswordByUser(String username) {
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
            e.printStackTrace();
            return password;
        }
    }
}
