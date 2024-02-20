package han.jvk.spotitube.persistance;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;

public interface ITokenDAO {
    String findUserByToken(String token);

    void saveAuthenticatedUser(AuthenticatedUserDTO uathDTO);
}
