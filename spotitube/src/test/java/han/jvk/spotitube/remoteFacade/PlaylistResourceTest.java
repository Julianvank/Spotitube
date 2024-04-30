package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.exception.APIException;
import han.jvk.spotitube.service.IPlaylistService;
import han.jvk.spotitube.service.ITokenService;
import han.jvk.spotitube.service.ITrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistResourceTest {

    PlaylistResource sut;
    IPlaylistService playlistMock;
    ITrackService trackMock;
    ITokenService tokenMock;

    AuthenticatedUserDTO authUser = new AuthenticatedUserDTO("username", "token");

    PlaylistDTO playlistDTO;
    PlaylistCollectionDTO collectionDTO;
    @BeforeEach
    void setUp() throws APIException {
        setUpMock();
        playlistDTO = new PlaylistDTO();
        collectionDTO = new PlaylistCollectionDTO();
        collectionDTO.putPlaylist(playlistDTO);

    }


    private void setUpMock() throws APIException {
        sut = new PlaylistResource();
        playlistMock = mock(IPlaylistService.class);
        trackMock = mock(ITrackService.class);
        tokenMock = mock(ITokenService.class);

        sut.setTrackService(trackMock);
        sut.setPlaylistService(playlistMock);
        sut.setTokenService(tokenMock);

    }

    @Test
    void getAllPlaylistTrue() throws APIException {
        when(playlistMock.getAllPlaylist(authUser)).thenReturn(collectionDTO);
        when(sut.validateToken("testToken")).thenReturn(authUser);

        int result = sut.getAllPlaylist("testToken").getStatus();

        assertEquals(200, result);
    }


}