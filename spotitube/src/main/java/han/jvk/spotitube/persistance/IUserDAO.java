package han.jvk.spotitube.persistance;

import han.jvk.spotitube.exception.PersistanceException;

public interface IUserDAO {
    String getPasswordByUser(String username) throws PersistanceException;
}
