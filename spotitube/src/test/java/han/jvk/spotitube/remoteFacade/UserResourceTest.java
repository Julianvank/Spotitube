package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.service.UserService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UserResourceTest {

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private UserResource sut;

    @BeforeEach
    public void setUp() {
        this.sut = new UserResource();
        openMocks(this);
    }

    @Test
    void loginUserTest_success(){
        //Arrange
        UserDTO input = new UserDTO("testUser", "testPassword", 1);
        AuthenticatedUserDTO expectedUser = new AuthenticatedUserDTO("testUser", "token");
        int expected = 200;

        when(userServiceMock.getUserToken(any())).thenReturn(expectedUser);
        //Act
        Response actual = sut.loginUser(input);
        //Assert
        assertEquals(expected, actual.getStatus());
        assertNotNull(actual.getEntity());
    }

    @Test
    void loginUserTest_NoFoundUser(){
        //Arrange
        UserDTO input = new UserDTO("testUser", "testPassword", 1);
        int expected = 401;

        when(userServiceMock.getUserToken(any())).thenReturn(null);
        //Act
        Response actual = sut.loginUser(input);
        //Assert
        assertEquals(expected, actual.getStatus());
        assertNull(actual.getEntity());
    }

}