package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.IUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private ITokenService tokenService;

    @Mock
    private IUserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to verify correct beviour
     * @throws DALException
     * @throws ServiceException
     */
    @Test
    void testGetUserToken_Success() throws DALException, ServiceException {
        // Arrange
        UserDTO user = new UserDTO("username", "password", 1);
        AuthenticatedUserDTO expectedAuthenticatedUserDTO = new AuthenticatedUserDTO("username", "token");

        when(userDAO.getPasswordByUser("username")).thenReturn("password");
        when(tokenService.generateAuthenticatedUserDTO(any(UserDTO.class))).thenReturn(expectedAuthenticatedUserDTO);

        // Act
        AuthenticatedUserDTO result = userService.getUserToken(user);

        // Assert
        assertEquals(expectedAuthenticatedUserDTO, result);
        verify(userDAO).getPasswordByUser("username");
        verify(tokenService).generateAuthenticatedUserDTO(user);
    }


    /**
     * Test to verify wrong password handeling
     * @throws DALException
     */
    @Test
    void testGetUserToken_AuthenticationFailed() throws DALException {
        // Arrange
        UserDTO user = new UserDTO("username", "password", 1);

        when(userDAO.getPasswordByUser("username")).thenReturn("notPassword");

        // Act
        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> userService.getUserToken(user)
        );

        //Assert
        assertEquals("Authentication failed; invalid login.", exception.getMessage());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, exception.getHttpStatusCode());
    }

    /**
     * Test to verify PersistanceException handeling.
     * @throws DALException
     */
    @Test
    void testGetUserToken_AuthenticationAssertThrowsPersistance() throws DALException {
        // Arrange
        UserDTO user = new UserDTO("username", "password", 1);

        when(userDAO.getPasswordByUser(anyString())).thenThrow(DALException.class);

        // Act
        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> userService.getUserToken(user)
        );

        //Assert
        assertEquals("Authentication failed; Could not retrieve data.", exception.getMessage());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, exception.getHttpStatusCode());
    }

}