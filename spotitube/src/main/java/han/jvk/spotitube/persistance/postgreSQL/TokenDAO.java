package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.persistance.DatabaseConnector;
import han.jvk.spotitube.persistance.ITokenDAO;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class TokenDAO extends DatabaseConnector implements ITokenDAO {

    private static final Map<String, String> userTokenStorage = new HashMap<>();

    @Override
    public String findUserByToken(String token) throws DALException {
        final String query = "SELECT username\n" +
                "    FROM users\n" +
                "        WHERE token = ?;";

        String user = null;

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, token);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                user = rs.getString(1);
            }
            return user;

        } catch (SQLException e) {
            throw new DALException(e);
        }
    }


    @Override
    public void saveAuthenticatedUser(AuthenticatedUserDTO authUser) {
        final String query = "UPDATE users SET token = ? WHERE username like ?";

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, authUser.getToken());
            stmt.setString(2, authUser.getUsername());


            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) throw new NoAffectedRowsException("No rows were affected.", 200);

        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
    }
}
