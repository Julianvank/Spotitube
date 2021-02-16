package Service;

import DTO.AuthenticatedUserDTO;
import DTO.UserDTO;
import Persistance.IUserDAO;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.xml.rpc.ServiceException;
import java.net.HttpURLConnection;

public class UserService implements IUserService{
    private ITokenService tokenService;
    private IUserDAO userDAO;

    @Inject
    public void setTokenService(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Inject
    public void setUserDAO(IUserDAO userDAO){this.userDAO = userDAO;}

    @Override
    public AuthenticatedUserDTO getAuthenticatedUserDTO(UserDTO userDTO) throws ServiceException {
        authenticate(userDTO);
        return tokenService.generateAuthenticatedUserDTO(userDTO);
    }


    /**
     * Authenticate de user en geeft een error als iets niet klopt.
     * @param userDTO De gegevens om te authenticaten
     * @throws ServiceException
     */
    private void authenticate(UserDTO userDTO) throws ServiceException {
        try {
            if (userDTO.getUsername().equals(userDAO.getPasswordByUser(userDTO.getPassword(), userDTO.getPassword()))) {
                logger.info("user logged in " + " " + userDTO.getUsername());
            } else {
                logger.warn("Authentication failed; invalid login.");
                throw new ServiceException("Authentication failed; invalid login.", HttpURLConnection.HTTP_BAD_REQUEST);
            }
        } catch (PersistenceException e) {
            logger.error(e);
            throw new ServiceException("Authentication failed; invalid login.", HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

}
