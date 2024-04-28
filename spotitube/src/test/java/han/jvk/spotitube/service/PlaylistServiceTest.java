package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.IPlaylistDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    void getAllPlaylistThrow() throws DALException {
        when(playlistDAO.getAllPlaylist(any())).thenReturn(new ArrayList<>());

        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> sut.getAllPlaylist(authUser)
        );
    }

    @Test
    void getAllPlaylist() throws ServiceException, DALException {
        // Arrange
        AuthenticatedUserDTO authUser = new AuthenticatedUserDTO();
        authUser.setUsername("testUser");

        List<TrackDTO> tracks = new ArrayList<>();
        TrackDTO track = new TrackDTO(1);
        tracks.add(track);

        PlaylistDTO playlist1 = new PlaylistDTO();
        playlist1.setName("Playlist 1");
        playlist1.setTracks(tracks);

        PlaylistDTO playlist2 = new PlaylistDTO();
        playlist2.setName("Playlist 2");
        playlist2.setTracks(tracks);

        List<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(playlist1);
        playlists.add(playlist2);

        when(trackService.getAllTracksFromPlaylist(any(), anyInt())).thenReturn(tracks);
        when(playlistDAO.getAllPlaylist("testUser")).thenReturn(playlists);
        // Act
        PlaylistCollectionDTO result = sut.getAllPlaylist(authUser);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getLength());
        assertEquals(2, result.getPlaylists().size());
        assertEquals("Playlist 1", result.getPlaylists().get(0).getName());
        assertEquals("Playlist 2", result.getPlaylists().get(1).getName());
    }

    @Test
    void deletePlaylistById() {
    }

    @Test
    void addPlaylist() {
    }

    @Test
    void editPlaylist() {
    }
}