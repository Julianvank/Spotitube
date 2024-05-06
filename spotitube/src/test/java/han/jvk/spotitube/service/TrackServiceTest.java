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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class TrackServiceTest {

    @InjectMocks
    TrackService sut;

    @Mock
    ITrackDAO trackDAO;

    @BeforeEach
    void setUp() {
        this.sut = new TrackService();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTracksFromPlaylist() {
        when(trackDAO.getAllTrackInPlaylist(anyInt())).thenReturn(new ArrayList<>());

        ServiceException exception = assertThrows(
                ServiceException.class,
                () -> sut.getAllTracksFromPlaylist(any(AuthenticatedUserDTO.class), 0)
        );

        assertEquals(HttpURLConnection.HTTP_OK ,exception.getHttpStatusCode());
    }

    @Test
    void getAvailableTracks() {
        when(trackDAO.getAvailableTracks(anyInt())).thenReturn(new ArrayList<>());

        ServiceException exception = assertThrows(
                ServiceException.class,
                () -> sut.getAvailableTracks(anyInt())
        );

        assertEquals(HttpURLConnection.HTTP_OK ,exception.getHttpStatusCode());
    }

    @Test
    void addTrackToPlaylist() {
        when(trackDAO.lookUpTrack(anyInt())).thenReturn(false);
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(0);
        ServiceException exception = assertThrows(
                ServiceException.class,
        () -> sut.addTrackToPlaylist(0, trackDTO)
        );

        assertEquals(HttpURLConnection.HTTP_CONFLICT, exception.getHttpStatusCode());
    }

    @Test
    void addTrackToPlaylistNoRows() {
        when(trackDAO.lookUpTrack(anyInt())).thenReturn(true);
        doThrow(NoAffectedRowsException.class).when(trackDAO).addTrackToPlaylist(anyInt(), anyInt());
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(0);

        assertThrows(
                ServiceException.class,
                () -> sut.addTrackToPlaylist(0, trackDTO)
        );
    }

    @Test
    void removeTrackFromPlaylist() {
        doThrow(NoAffectedRowsException.class).when(trackDAO).removeTrackFromPlaylist(0, 0);

        assertThrows(
                ServiceException.class,
                () -> sut.removeTrackFromPlaylist(any(), anyInt(), 0)
        );
    }
}