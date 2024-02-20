package han.jvk.spotitube.persistance.local;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.persistance.ITokenDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TokenDAO implements ITokenDAO {

    private static final Map<String, String> userTokenStorage = new HashMap<>();

    @Override
    public String findUserByToken(String givenToken) {
        String user = null;
        Optional<Map.Entry<String, String>> tokenEntry =
                userTokenStorage.entrySet().stream()
                        .filter(t -> {
                            String token = t.getKey();
                            return givenToken.equals(token);
                        }).findFirst();

        if (tokenEntry.isPresent()) {
            user = tokenEntry.get().getValue();
        }

        return user;
    }

    @Override
    public void saveAuthenticatedUser(AuthenticatedUserDTO uathDTO) {
        userTokenStorage.put(uathDTO.getToken(), uathDTO.getUsername());
    }
}
