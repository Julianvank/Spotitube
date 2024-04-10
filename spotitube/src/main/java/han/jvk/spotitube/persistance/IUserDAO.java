package han.jvk.spotitube.persistance;

import han.jvk.spotitube.dto.exception.PersistanceException;

public interface IUserDAO {
    String getPasswordByUser(String username) throws PersistanceException;
}
