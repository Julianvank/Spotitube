package han.jvk.spotitube.service;

import java.net.HttpURLConnection;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.PersistanceException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.IUserDAO;
import jakarta.inject.Inject;

public class UserService implements IUserService{

    ITokenService tokenService;
    IUserDAO userDAO;

    @Inject
    public void setTokenService(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Inject
    public void setUserDAO(IUserDAO userDAO){this.userDAO = userDAO;}

    @Override
    public AuthenticatedUserDTO getUserToken(UserDTO user) throws ServiceException {
        authenticate(user);
        return tokenService.generateAuthenticatedUserDTO(user);
    }

    private void authenticate(UserDTO user) throws ServiceException {
        try {
            if (user.getUsername().equals(userDAO.getPasswordByUser(user.getUsername(), user.getPassword()))) {
                System.out.println("User logged in");
            } else {
                System.out.println("Incorrect login");
                throw new ServiceException("Authentication failed; invalid login.", HttpURLConnection.HTTP_BAD_REQUEST);
            }
        } catch (PersistanceException e) {
            e.printStackTrace();
            throw new ServiceException("Authentication failed; invalid login.", HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }
}