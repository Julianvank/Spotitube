package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.dto.exception.PersistanceException;
import han.jvk.spotitube.dto.exception.ServiceException;
import han.jvk.spotitube.persistance.IUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    @Test
    public void testGetUserToken_Success() throws PersistanceException, ServiceException {
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

    @Test
    public void testGetUserToken_AuthenticationFailed() throws PersistanceException {
        // Arrange
        UserDTO user = new UserDTO("username", "password", 1);

        when(userDAO.getPasswordByUser("username")).thenThrow(new PersistanceException());

        // Act
        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> userService.getUserToken(user)
        );

        //Assert
        assertEquals("Authentication failed; invalid login.", exception.getMessage());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, exception.getHttpStatusCode());
    }

    @Test
    public void testGetUserToken_InvalidPassword() throws PersistanceException {
        // Arrange
        UserDTO user = new UserDTO("username", "wrong_password",1);

        // Mock behavior of userDAO to return a password
        when(userDAO.getPasswordByUser("username")).thenReturn("password");

        // Act & Assert
        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> userService.getUserToken(user)
        );

        assertEquals("Authentication failed; invalid login.", exception.getMessage());
        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, exception.getHttpStatusCode());
    }
}