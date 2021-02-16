package Service;

import DTO.AuthenticatedUserDTO;
import DTO.UserDTO;

public interface ITokenService {
    AuthenticatedUserDTO generateAuthenticatedUserDTO(UserDTO userDTO);
}
