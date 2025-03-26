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

    @Override
    public String findUserByToken(String token) throws DALException {
        String user = null;

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(FIND_USER_BY_TOKEN_QUERY);

            stmt.setString(1, token);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = rs.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.");
        }
        return user;
    }

    @Override
    public void saveAuthenticatedUser(AuthenticatedUserDTO authUser) {

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(UPDATE_USER_TOKEN_QUERY);

            stmt.setString(1, authUser.getToken());
            stmt.setString(2, authUser.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.");
        }
    }

    private static final String FIND_USER_BY_TOKEN_QUERY = "SELECT username\n" +
            "    FROM users\n" +
            "        WHERE token = ?;";

    private static final String UPDATE_USER_TOKEN_QUERY = "UPDATE users SET token = ? WHERE username like ?";
}
