package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.APIException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.service.IUserService;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserResourceTest {

    @Test
    void loginUserTrue() throws APIException {
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
    void loginUserThrows() throws APIException {
        UserResource sut = new UserResource();
        IUserService serviceMock = mock(IUserService.class);

        UserDTO testUser = new UserDTO("test", "test", 1);

        when(serviceMock.getUserToken(testUser)).thenThrow(new ServiceException("invalid login", HttpURLConnection.HTTP_BAD_REQUEST));
        sut.setUserService(serviceMock);

        assertThrows(APIException.class, () -> sut.loginUser(testUser).getStatus());
    }
}