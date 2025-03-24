package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.IPlaylistDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PlaylistServiceTest {

    @Mock
    IPlaylistDAO playlistDAO;
    @Mock
    ITrackService trackService;

    @InjectMocks
    PlaylistService sut;

    AuthenticatedUserDTO authUser = new AuthenticatedUserDTO("username", "token");

    @BeforeEach
    void setUp() {
        this.sut = new PlaylistService();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPlaylist_NoPlaylists() throws ServiceException {
        // Arrange
        AuthenticatedUserDTO authUser = new AuthenticatedUserDTO("testUser", "abcdef");
        when(playlistDAO.getAllPlaylist(authUser.getUsername())).thenReturn(Collections.emptyList());

        // Act
        PlaylistCollectionDTO result = sut.getAllPlaylist(authUser);

        // Assert
        assertNotNull(result);
        assertTrue(result.getPlaylists().isEmpty());
        assertEquals(0, result.getLength());
    }

    @Test
    public void testGetAllPlaylist_WithPlaylists() throws ServiceException {
        // Arrange
        AuthenticatedUserDTO authUser = new AuthenticatedUserDTO("testUser", "abcdef");
        PlaylistDTO playlist1 = new PlaylistDTO(1, "Playlist 1", "user1", Collections.emptyList());
        PlaylistDTO playlist2 = new PlaylistDTO(2, "Playlist 2", "user1", Collections.emptyList());
        List<PlaylistDTO> playlists = List.of(playlist1, playlist2);

        when(playlistDAO.getAllPlaylist(authUser.getUsername())).thenReturn(playlists);
        when(trackService.getAllTracksFromPlaylist(any(), anyInt())).thenReturn(Collections.emptyList());

        // Act
        PlaylistCollectionDTO result = sut.getAllPlaylist(authUser);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getPlaylists().size());
    }
}
