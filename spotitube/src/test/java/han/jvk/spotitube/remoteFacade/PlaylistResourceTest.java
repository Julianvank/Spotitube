package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.APIException;
import han.jvk.spotitube.service.*;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistResourceTest {

    @InjectMocks
    PlaylistResource sut;

    @Mock
    IPlaylistService playlistServiceMock;

    @Mock
    ITokenService tokenServiceMock;

    private AuthenticatedUserDTO authUser = new AuthenticatedUserDTO("user", "token");
    private final String input = "userToken";

    PlaylistResourceTest(){
        sut = new PlaylistResource();
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllPlaylistTest_Success(){
        //Arrange
        int expectedCode = 200;
        Mockito.when(playlistServiceMock.getAllPlaylist(authUser)).thenReturn(playlistsFixture());
        Mockito.when(tokenServiceMock.getAuthenticatedUserDTOByToken(any())).thenReturn(authUser);
        //Act
        Response actual = sut.getAllPlaylist(input);

        //Assert
        assertEquals(expectedCode, actual.getStatus());
        assertNotNull(actual.getEntity());
    }

    @Test
    void getAllPlaylistTest_ContentNull(){
        //Arrange
        int expectedCode = 204;
        Mockito.when(playlistServiceMock.getAllPlaylist(authUser)).thenReturn(null);
        Mockito.when(tokenServiceMock.getAuthenticatedUserDTOByToken(any())).thenReturn(authUser);
        //Act
        Response actual = sut.getAllPlaylist(input);

        //Assert
        assertEquals(expectedCode, actual.getStatus());
        assertNull(actual.getEntity());
    }

    private PlaylistCollectionDTO playlistsFixture(){
        TrackDTO track = new TrackDTO(
                1,
                "Shape of You",
                "Ed Sheeran",
                240,
                "Divide",
                1000000,
                "2017-01-06",
                "A romantic pop song that became a global hit.",
                true
        );

        List<TrackDTO> tracks = new ArrayList<>();
        tracks.add(track);

        PlaylistDTO playlist = new PlaylistDTO(
                1,
                "Chill Vibes",
                "Alice",
                tracks
        );

        PlaylistCollectionDTO playlistCollection = new PlaylistCollectionDTO();
        playlistCollection.putPlaylist(playlist);
        playlistCollection.setLength(1);

        return playlistCollection;
    }
}
