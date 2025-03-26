package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.persistance.ITrackDAO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class TrackServiceTest {

    @InjectMocks
    ITrackService sut;

    @Mock
    ITrackDAO trackDaoMock;

    AuthenticatedUserDTO authUser = new AuthenticatedUserDTO("username", "token");

    TrackServiceTest() {
        sut = new TrackService();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTracksFromPlaylistTest_NoErrorEncountered() {
        //Act
        sut.getAllTracksFromPlaylist(authUser, 1);

        //Assert
        verify(trackDaoMock).getAllTracksInPlaylist(anyInt());
    }

    @Test
    void getAvailableTracksTest_NoErrorEncountered() {
        //Act
        sut.getAvailableTracks(1);

        //Assert
        verify(trackDaoMock).getAvailableTracks(anyInt());
    }

    @Test
    void addTrackToPlaylistTest_TrackInDatabase() {
        //Arrange
        TrackDTO trackDTO = new TrackDTO();
        when(trackDaoMock.lookUpTrack(anyInt())).thenReturn(true);

        //Act
        sut.addTrackToPlaylist(1, trackDTO);

        //Assert
        verify(trackDaoMock).addTrackToPlaylist(anyInt(), anyInt());
    }

    @Test
    void addTrackToPlaylistTest_TrackNotInDatabase() {
        //Arrange
        TrackDTO trackDTO = new TrackDTO();
        when(trackDaoMock.lookUpTrack(anyInt())).thenReturn(false);

        //Act
        sut.addTrackToPlaylist(1, trackDTO);

        //Assert
        verify(trackDaoMock, never()).addTrackToPlaylist(anyInt(), anyInt());
    }

    @Test
    void removeTrackFromPlaylistTest_NoErrorEncountered(){
        //Act
        sut.removeTrackFromPlaylist(authUser, 1, 1);
        //Assert
        verify(trackDaoMock).removeTrackFromPlaylist(anyInt(), anyInt());
    }
}