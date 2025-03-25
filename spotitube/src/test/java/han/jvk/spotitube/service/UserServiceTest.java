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

    @Test
    void getUserTokenTest_Success() throws DALException, ServiceException {
        // Arrange
        UserDTO user = new UserDTO("username", "password", 1);
        AuthenticatedUserDTO expected = new AuthenticatedUserDTO("username", "token");

        when(userDAO.getPasswordByUser("username")).thenReturn("password");
        when(tokenService.generateAuthenticatedUserDTO(any(UserDTO.class))).thenReturn(expected);

        // Act
        AuthenticatedUserDTO result = userService.getUserToken(user);

        // Assert
        assertEquals(expected, result);
        verify(userDAO).getPasswordByUser("username");
        verify(tokenService).generateAuthenticatedUserDTO(user);
    }



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
        assertEquals(HttpURLConnection.HTTP_FORBIDDEN, exception.getHttpStatusCode());
    }


    @Test
    void testGetUserToken_AuthenticationAssertThrowsDALException() throws DALException {
        // Arrange
        UserDTO user = new UserDTO("username", "password", 1);

        when(userDAO.getPasswordByUser(anyString())).thenThrow(DALException.class);

        // Act
        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> userService.getUserToken(user)
        );

        //Assert
        assertEquals(HttpURLConnection.HTTP_UNAVAILABLE, exception.getHttpStatusCode());
    }

}