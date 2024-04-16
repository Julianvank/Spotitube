package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.ServiceException;


public interface IUserService {
    AuthenticatedUserDTO getUserToken(UserDTO user) throws ServiceException;
}
