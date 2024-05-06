package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.ITokenDAO;
import han.jvk.spotitube.util.factory.Token.ITokenFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.net.HttpURLConnection;

@ApplicationScoped
public class TokenService implements ITokenService{

    private ITokenDAO tokenDAO;
    private ITokenFactory tokenFactory;

    @Inject
    public void setTokenDAO(ITokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    @Inject
    public void setTokenFactory(ITokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    @Override
    public AuthenticatedUserDTO getAuthenticatedUserDTOByToken(String token) throws ServiceException {
        if (token.isEmpty()) throw new ServiceException("Missing token.", HttpURLConnection.HTTP_FORBIDDEN);
        String user = tokenDAO.findUserByToken(token);

        if (user == null || user.isEmpty()) throw new ServiceException("Invalid token.", HttpURLConnection.HTTP_UNAUTHORIZED);
        return new AuthenticatedUserDTO(user, token);
    }

    @Override
    public AuthenticatedUserDTO generateAuthenticatedUserDTO(UserDTO userDTO) {
        AuthenticatedUserDTO authDTO = new AuthenticatedUserDTO(userDTO.getUsername(), tokenFactory.generateToken());

        tokenDAO.saveAuthenticatedUser(authDTO);
        return authDTO;
    }
}
