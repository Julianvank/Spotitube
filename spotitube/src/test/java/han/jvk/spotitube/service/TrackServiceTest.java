package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.APIException;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.exception.RestException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.ITrackDAO;
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
import static org.mockito.ArgumentMatchers.any;
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
    void addTracksToPlaylistTest_TrackInDatabase() {
        //Arrange
        TrackDTO trackDTO = new TrackDTO();
        when(trackDaoMock.lookUpTrack(anyInt())).thenReturn(true);

        //Act
        sut.addTrackToPlaylist(1, trackDTO);

        //Assert
        verify(trackDaoMock).addTrackToPlaylist(anyInt(), anyInt());
    }
}