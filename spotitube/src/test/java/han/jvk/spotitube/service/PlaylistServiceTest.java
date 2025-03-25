package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.IPlaylistDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    ITrackService trackServiceMock;

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
        when(trackServiceMock.getAllTracksFromPlaylist(any(), anyInt())).thenReturn(Collections.emptyList());

        // Act
        PlaylistCollectionDTO result = sut.getAllPlaylist(authUser);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getPlaylists().size());
    }

    @Test
    void getPlaylistTest_findPlaylist(){
        //Arrange
        PlaylistDTO playlist = new PlaylistDTO(1, "Playlist 1", "user1", Collections.emptyList());
        when(playlistDAO.getPlaylist(any(), anyInt()))
                .thenReturn(playlist);

        //Act
        PlaylistDTO actual = sut.getPlaylist(authUser, 1);
        //Assert
        assertNotNull(actual);
    }

    @Test
    void deletePlayListTest_noErrorEncountered(){
        //Act
        sut.deletePlaylistById(authUser, 1);
        //Arrange
        verify(playlistDAO).deletePlaylistById(anyString(), anyInt());
    }

    @Test
    void deletePlaylistTest_ErrorEncountered(){
        //Arrange
        doThrow(new NoAffectedRowsException("test Error"))
                .when(playlistDAO).deletePlaylistById(anyString(), anyInt());

        //Act & Assert
        assertThrows(ServiceException.class,
                () -> sut.deletePlaylistById(authUser, 1));

    }

    @Test
    void addPlaylist_NoErrorEncountered(){
        //Arrange
        PlaylistDTO playlist = new PlaylistDTO(-1, "Playlist 1", "user1", Collections.emptyList());
        //Act
        sut.addPlaylist(authUser, playlist);
        //Assert
        verify(playlistDAO).addPlaylist(anyString(), any());
        verify(playlistDAO).addTracksToPlaylist(anyString(), any(), anyInt());
    }

    @Test
    void addPlaylistTest_NoAffectedRowsExceptionEncountered(){
        //Arrange
        PlaylistDTO playlist = new PlaylistDTO(-1, "Playlist 1", "user1", Collections.emptyList());
        doThrow(new NoAffectedRowsException("test Error"))
                .when(playlistDAO).addPlaylist(anyString(), any());

        //Act & Assert
        ServiceException exception = assertThrows(ServiceException.class,
                () -> sut.addPlaylist(authUser, playlist));
        assertEquals(204, exception.getHttpStatusCode());
    }

    @Test
    void addPlaylistTest_IncorrectPlaylistId(){
        PlaylistDTO playlist = new PlaylistDTO(1, "Playlist 1", "user1", Collections.emptyList());


        //Act & Assert
        ServiceException exception = assertThrows(ServiceException.class,
                () -> sut.addPlaylist(authUser, playlist));
        assertEquals(400, exception.getHttpStatusCode());
    }


    @Test
    void editPlaylistTest_NoErrorEncountered(){
        //Arrange
        PlaylistDTO playlist = new PlaylistDTO(1, "Playlist 1", "user1", Collections.emptyList());
        //Act
        sut.editPlaylist(playlist, 1);
        //Assert
        verify(playlistDAO).editPlaylist(any(), anyInt());
    }

    @Test
    void editPlaylistTest_EncounteredNoRowsAffectedException(){
        //Assert
        PlaylistDTO playlist = new PlaylistDTO(1, "Playlist 1", "user1", Collections.emptyList());
        doThrow(new NoAffectedRowsException("test Error"))
                .when(playlistDAO).editPlaylist(any(), anyInt());

        //Act & Assert
        ServiceException exception = assertThrows(ServiceException.class,
                () -> sut.editPlaylist(playlist, 1));
        assertEquals(204, exception.getHttpStatusCode());
    }

    @Test
    void getAllPlaylist_setsCorrectLength(){
        // Arrange
        int expectedLength = 750;

        TrackDTO track1 = new TrackDTO(1, "Track 1", "Artist 1", 200, "Album 1", 100, "2021-01-01", "Description 1", true);
        TrackDTO track2 = new TrackDTO(2, "Track 2", "Artist 2", 300, "Album 2", 150, "2021-01-02", "Description 2", false);
        TrackDTO track3 = new TrackDTO(3, "Track 3", "Artist 3", 250, "Album 3", 200, "2021-01-03", "Description 3", true);

        List<TrackDTO> tracks1 = new ArrayList<>();
        tracks1.add(track1);
        tracks1.add(track2);

        List<TrackDTO> tracks2 = new ArrayList<>();
        tracks2.add(track3);

        PlaylistDTO playlist1 = new PlaylistDTO(1, "Playlist 1", "Owner 1", tracks1);
        PlaylistDTO playlist2 = new PlaylistDTO(2, "Playlist 2", "Owner 2", tracks2);

        List<PlaylistDTO> playlists = new ArrayList<>();
        playlists.add(playlist1);
        playlists.add(playlist2);

        when(playlistDAO.getAllPlaylist(anyString())).thenReturn(playlists);
        when(trackServiceMock.getAllTracksFromPlaylist(authUser, 1))
                .thenReturn(tracks1);
        when(trackServiceMock.getAllTracksFromPlaylist(authUser, 2))
                .thenReturn(tracks2);
        // Act
        PlaylistCollectionDTO actual = sut.getAllPlaylist(authUser);

        // Assert
        assertEquals(expectedLength, actual.getLength());
    }
}
