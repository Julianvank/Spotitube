package han.jvk.spotitube.persistance;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.exception.DALException;

public interface ITokenDAO {
    String findUserByToken(String token) throws DALException;

    void saveAuthenticatedUser(AuthenticatedUserDTO authUser);
}
