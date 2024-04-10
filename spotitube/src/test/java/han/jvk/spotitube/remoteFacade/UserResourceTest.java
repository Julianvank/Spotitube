package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.dto.exception.RestException;
import han.jvk.spotitube.dto.exception.ServiceException;
import han.jvk.spotitube.service.IUserService;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserResourceTest {

    @Test
    void loginUserTrue() throws ServiceException, RestException {
        UserResource sut = new UserResource();
        IUserService serviceMock = mock(IUserService.class);

        UserDTO testUser = new UserDTO("test", "test", 1);
        AuthenticatedUserDTO authUser = new AuthenticatedUserDTO("username", "token");

        when(serviceMock.getUserToken(any())).thenReturn(authUser);
        sut.setUserService(serviceMock);

        int result = sut.loginUser(testUser).getStatus();

        assertEquals(200, result);
    }

    @Test
    void loginUserThrows() throws ServiceException, RestException {
        UserResource sut = new UserResource();
        IUserService serviceMock = mock(IUserService.class);

        UserDTO testUser = new UserDTO("test", "test", 1);
        AuthenticatedUserDTO authUser = new AuthenticatedUserDTO("username", "token");

        when(serviceMock.getUserToken(testUser)).thenThrow(new ServiceException("invalid login", HttpURLConnection.HTTP_BAD_REQUEST));
        sut.setUserService(serviceMock);

        assertThrows(RestException.class, () -> {
            sut.loginUser(testUser).getStatus();
        });
    }
}