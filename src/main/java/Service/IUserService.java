package Service;

import DTO.AuthenticatedUserDTO;
import DTO.UserDTO;

public interface IUserService {
    AuthenticatedUserDTO getAuthenticatedUserDTO(UserDTO userDTO);
}
