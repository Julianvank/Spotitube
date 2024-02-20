package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.ServiceException;

public interface ITokenService {

    AuthenticatedUserDTO getAuthenticatedUserDTOByToken(String token) throws ServiceException;

    AuthenticatedUserDTO generateAuthenticatedUserDTO(UserDTO userDTO) throws ServiceException;
}
