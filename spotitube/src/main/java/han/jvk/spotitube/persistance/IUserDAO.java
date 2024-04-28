package han.jvk.spotitube.persistance;

import han.jvk.spotitube.exception.DALException;

public interface IUserDAO {
    String getPasswordByUser(String username) throws DALException;
}
