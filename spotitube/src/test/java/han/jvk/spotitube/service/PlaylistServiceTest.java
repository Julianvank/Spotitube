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
    void getAllPlaylistThrow() throws DALException {
        when(playlistDAO.getAllPlaylist(anyString())).thenReturn(new ArrayList<>());

        ServiceException exception = Assertions.assertThrows(
                ServiceException.class,
                () -> sut.getAllPlaylist(authUser)
        );

        assertEquals(HttpURLConnection.HTTP_CONFLICT, exception.getHttpStatusCode());
    }

    @Test
    void getAllPlaylist() throws ServiceException, DALException {
        int[] trackAmount = {3, 8};
        int expectedLength = 0;
        for (int j : trackAmount) {
            expectedLength += j;
        }
        // Arrange
        AuthenticatedUserDTO authUser = new AuthenticatedUserDTO();
        authUser.setUsername("testUser");

        PlaylistDTO playlist1 = getPlaylistDTO(getTrackDTOS(trackAmount[0]), "Playlist 1", 0);
        PlaylistDTO playlist2 = getPlaylistDTO(getTrackDTOS(trackAmount[1]), "Playlist 2", 1);

        List<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(playlist1);
        playlists.add(playlist2);

        for(int i = 0; i < trackAmount.length; i++){
            when(trackService.getAllTracksFromPlaylist(authUser, i)).thenReturn(getTrackDTOS(trackAmount[i]));
        }

        when(playlistDAO.getAllPlaylist("testUser")).thenReturn(playlists);
        // Act
        PlaylistCollectionDTO result = sut.getAllPlaylist(authUser);

        // Assert
        assertNotNull(result);
        assertEquals(expectedLength, result.getLength());
        assertEquals(trackAmount.length, result.getPlaylists().size());
        assertEquals("Playlist 1", result.getPlaylists().get(0).getName());
        assertEquals("Playlist 2", result.getPlaylists().get(1).getName());
    }

    //TODO write getLength test which uses different numbers.
    private static List<TrackDTO> getTrackDTOS(int amount) {
        List<TrackDTO> tracks = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            TrackDTO track = new TrackDTO(1);
            tracks.add(track);
        }

        return tracks;
    }

    private static PlaylistDTO getPlaylistDTO(List<TrackDTO> tracks, String name, int id) {
        PlaylistDTO playlist = new PlaylistDTO();
        playlist.setName(name);
        playlist.setTracks(tracks);
        playlist.setId(id);
        return playlist;
    }

    @Test
    void getPlaylist() {
        PlaylistDTO expected = getPlaylistDTO(getTrackDTOS(5), "Playlist 1", 0);

        when(playlistDAO.getPlaylist(anyString(), anyInt())).thenReturn(expected);

        PlaylistDTO actual = sut.getPlaylist(authUser, 0);

        assertEquals(expected, actual);
    }

    @Test
    void getPlaylistThrows() {
        when(playlistDAO.getPlaylist(anyString(), anyInt())).thenReturn(null);

        //Assert

        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> sut.getPlaylist(authUser, 0)
        );
        assertEquals(HttpURLConnection.HTTP_CONFLICT, exception.getHttpStatusCode());

    }

    @Test
    void deletePlaylistById() {
        doThrow(NoAffectedRowsException.class).when(playlistDAO).deletePlaylistById(anyString(), anyInt());

        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> sut.deletePlaylistById(authUser, 0)
        );
        assertEquals(HttpURLConnection.HTTP_CONFLICT, exception.getHttpStatusCode());
    }

    @Test
    void addPlaylistIncorrectId() {
        PlaylistDTO playlistDTO = getPlaylistDTO(getTrackDTOS(0), "playlist 1", 0);
        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> sut.addPlaylist(authUser, playlistDTO)
        );
        assertEquals(HttpURLConnection.HTTP_CONFLICT, exception.getHttpStatusCode());
    }

    @Test
    void addPlaylistExceptionThrownAddPlaylist() {
        doThrow(new NoAffectedRowsException("test", 200)).when(playlistDAO).addPlaylist(anyString(), any());
        PlaylistDTO playlistDTO = getPlaylistDTO(getTrackDTOS(0), "playlist 1", -1);
        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> sut.addPlaylist(authUser, playlistDTO)
        );
        assertEquals(HttpURLConnection.HTTP_OK, exception.getHttpStatusCode());
    }

    @Test
    void addPlaylistExceptionThrownAddTracks() {
        doThrow(new NoAffectedRowsException("test", 200)).when(playlistDAO).addTracksToPlaylist(anyString(), any(), anyInt());
        PlaylistDTO playlistDTO = getPlaylistDTO(getTrackDTOS(0), "playlist 1", -1);
        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> sut.addPlaylist(authUser, playlistDTO)
        );
        assertEquals(HttpURLConnection.HTTP_OK, exception.getHttpStatusCode());
    }

    @Test
    void editPlaylist() {
        doThrow(new NoAffectedRowsException("test", 200)).when(playlistDAO).editPlaylist(any(), anyInt());

        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> sut.editPlaylist(any(), anyInt())
        );
        assertEquals(HttpURLConnection.HTTP_OK, exception.getHttpStatusCode());
    }
}