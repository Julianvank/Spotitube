package Service;

import DTO.AuthenticatedUserDTO;
import DTO.UserDTO;

import javax.xml.rpc.ServiceException;

public interface IUserService {
    AuthenticatedUserDTO getAuthenticatedUserDTO(UserDTO userDTO) throws ServiceException;
}
