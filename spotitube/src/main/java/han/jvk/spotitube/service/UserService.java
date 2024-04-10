package han.jvk.spotitube.service;

import java.net.HttpURLConnection;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.dto.exception.PersistanceException;
import han.jvk.spotitube.dto.exception.ServiceException;
import han.jvk.spotitube.persistance.IUserDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService implements IUserService {

    private ITokenService tokenService;
    private IUserDAO userDAO;

    @Inject
    public void setTokenService(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Inject
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public AuthenticatedUserDTO getUserToken(UserDTO user) throws ServiceException {
        authenticate(user);
        return tokenService.generateAuthenticatedUserDTO(user);
    }

    private void authenticate(UserDTO user) throws ServiceException {
        try {
            if (!user.getPassword().equals(userDAO.getPasswordByUser(user.getUsername()))) {
                throw new ServiceException("Authentication failed; invalid login.", HttpURLConnection.HTTP_BAD_REQUEST);
            }
        } catch (PersistanceException e) {
            e.printStackTrace();
            throw new ServiceException("Authentication failed; invalid login.", HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }
}