package han.jvk.spotitube.service;

import java.net.HttpURLConnection;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.IUserDAO;
import han.jvk.spotitube.remoteFacade.TokenRequiredResource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserService implements IUserService {

    private static final Logger log = Logger.getLogger(TokenRequiredResource.class.getName());

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
                throw new ServiceException("invalid login.", HttpURLConnection.HTTP_FORBIDDEN);
            }
        } catch (DALException e) {
            throw new ServiceException("Could not retrieve data.", HttpURLConnection.HTTP_UNAVAILABLE);
        }
    }
}