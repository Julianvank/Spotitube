package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.IUserDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.net.HttpURLConnection;

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
    public AuthenticatedUserDTO getUserToken(UserDTO userDTO) throws ServiceException {
        authenticate(userDTO);
        return tokenService.generateAuthenticatedUserDTO(userDTO);
    }

    private void authenticate(UserDTO userDTO) throws ServiceException {
        try {
            if (!userDTO.getPassword().equals(userDAO.getPasswordByUser(userDTO.getUsername()))) {
                throw new ServiceException("invalid login info.", HttpURLConnection.HTTP_FORBIDDEN);
            }
        } catch (DALException e) {
            throw new ServiceException("Could not retrieve data.", HttpURLConnection.HTTP_UNAVAILABLE);
        }
    }
}