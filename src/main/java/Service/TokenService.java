package Service;

import DTO.AuthenticatedUserDTO;
import DTO.UserDTO;

public class TokenService implements ITokenService{

    private ITokenDAO tokenDAO;
    private ITokenFactory tokenFactory;

    @Override
    public AuthenticatedUserDTO generateAuthenticatedUserDTO(UserDTO userDTO) {
        AuthenticatedUserDTO auDTO = new AuthenticatedUserDTO(userDTO.getUsername(), tokenFactory.generateToken());
        return null;
    }
}
