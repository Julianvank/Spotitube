package han.jvk.spotitube.persistance.local;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.persistance.ITokenDAO;
import han.jvk.spotitube.persistance.postgreSQL.PostgresConnector;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class TokenDAO extends PostgresConnector implements ITokenDAO {

    private static final Map<String, String> userTokenStorage = new HashMap<>();

    public TokenDAO(){
        userTokenStorage.put("test", "Admin");
    }

//    @Override
//    public String findUserByToken(String givenToken) {
//        String user = null;
//        Optional<Map.Entry<String, String>> tokenEntry =
//                userTokenStorage.entrySet().stream()
//                        .filter(t -> {
//                            String token = t.getKey();
//                            return givenToken.equals(token);
//                        }).findFirst();
//
//        if (tokenEntry.isPresent()) {
//            user = tokenEntry.get().getValue();
//        }
//
//        return user;
//    }

    @Override
    public String findUserByToken(String token){
        final String query = "SELECT username\n" +
                "    FROM users\n" +
                "        WHERE token = ?;";

        String user = null;

        System.out.println(token);

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, token);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                user = rs.getString(1);
            }
            System.out.println("User = " + user);
            return user;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }



    @Override
    public void saveAuthenticatedUser(AuthenticatedUserDTO uathDTO) {
        userTokenStorage.put(uathDTO.getToken(), uathDTO.getUsername());
    }
}
