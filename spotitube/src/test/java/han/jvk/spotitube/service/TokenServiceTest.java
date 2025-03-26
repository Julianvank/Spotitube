package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.ITokenDAO;
import han.jvk.spotitube.util.factory.Token.ITokenFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TokenServiceTest {

    @InjectMocks
    ITokenService sut;

    @Mock
    ITokenDAO tokenDAO;

    @Mock
    ITokenFactory tokenFactory;

    @BeforeEach
    void setUp() {
        this.sut = new TokenService();

        MockitoAnnotations.openMocks(this);
    }
//    doThrow(new NoAffectedRowsException("test", 200)).when(playlistDAO).editPlaylist(any(), anyInt());

    @Test
    void getAuthenticatedUserDTOByToken() {
        String string = "token";
        AuthenticatedUserDTO expected = new AuthenticatedUserDTO("username", "token");
        when(tokenDAO.findUserByToken(string)).thenReturn("username");

        AuthenticatedUserDTO actual = sut.getAuthenticatedUserDTOByToken(string);

        assertEquals(expected.getToken(), actual.getToken());
        assertEquals(expected.getUsername(), actual.getUsername());
    }

    @Test
    void getAuthenticatedUserDTOByTokenMissingToken() {
        String string = "";
        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> sut.getAuthenticatedUserDTOByToken(string)
        );
        assertEquals(HttpURLConnection.HTTP_FORBIDDEN, exception.getHttpStatusCode());
        assertEquals("Missing token.", exception.getMessage());
    }

    @Test
    void getAuthenticatedUserDTOByTokenInvalidToken() {
        when(tokenDAO.findUserByToken(anyString())).thenReturn(null);
        String string = "test";

        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> sut.getAuthenticatedUserDTOByToken(string)
        );
        assertEquals("Invalid token.", exception.getMessage());

        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, exception.getHttpStatusCode());
    }

    @Test
    void generateAuthenticatedUserDTO(){
        when(tokenFactory.generateToken()).thenReturn("Token");

        UserDTO user = new UserDTO("user", "password", 1);
        AuthenticatedUserDTO expected = new AuthenticatedUserDTO("user", "Token");

        AuthenticatedUserDTO actual = sut.generateAuthenticatedUserDTO(user);

        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getToken(), actual.getToken());

    }

}