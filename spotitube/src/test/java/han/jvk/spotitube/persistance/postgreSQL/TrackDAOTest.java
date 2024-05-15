package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.util.factory.DBConnection.IDBConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class TrackDAOTest {
    @InjectMocks
    TrackDAO sut;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPrepStatement;
    @Mock
    Statement mockStatement;
    @Mock
    ResultSet mockResultSet;
    @Mock
    IDBConnectionFactory connector;

    @BeforeEach
    void setUp() throws SQLException {
        this.sut = new TrackDAO();
        sut.setConnector(connector);

        MockitoAnnotations.openMocks(this);

        when(connector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPrepStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockPrepStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    }


    @Test
    public void testGetAllTrackInPlaylist() throws SQLException, DALException {
        // Arrange
        int playlistId = 1;
        List<TrackDTO> expectedTracks = new ArrayList<>();
        expectedTracks.add(new TrackDTO());
        expectedTracks.add(new TrackDTO());

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(expectedTracks.get(0).getId(), expectedTracks.get(1).getId());

        // Act
        List<TrackDTO> actualTracks = sut.getAllTracksInPlaylist(playlistId);

        // Assert
        assertEquals(expectedTracks.size(), actualTracks.size());
    }

    @Test
    public void testGetAllTrackInPlaylistThrow() throws SQLException, DALException {
        when(mockPrepStatement.executeQuery()).thenThrow(new SQLException());

        // No need for an assertion as the test expects an exception
        assertThrows(
                DALException.class,
                () -> sut.getAllTracksInPlaylist(0)
        );
    }

    @Test
    void getAvailableTracks() throws SQLException {
        // Arrange
        int playlistId = 1;
        List<TrackDTO> expectedTracks = new ArrayList<>();
        expectedTracks.add(new TrackDTO());
        expectedTracks.add(new TrackDTO());

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(expectedTracks.get(0).getId(), expectedTracks.get(1).getId());

        // Act
        List<TrackDTO> actualTracks = sut.getAvailableTracks(playlistId);

        // Assert
        assertEquals(expectedTracks.size(), actualTracks.size());
    }

    @Test
    public void getAvailableTracksThrow() throws SQLException, DALException {
        when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException());

        // No need for an assertion as the test expects an exception
        assertThrows(
                DALException.class,
                () -> sut.getAvailableTracks(0)
        );
    }

    @Test
    void noAffectedRowThrow() throws SQLException {
        when(mockPrepStatement.executeUpdate()).thenReturn(0);

        Assertions.assertThrows(
                NoAffectedRowsException.class,
                () -> sut.addTrackToPlaylist(0, 0)
        );
    }

    @Test
    void executeQuery() throws SQLException {
        when(mockPrepStatement.executeUpdate()).thenThrow(SQLException.class);

        assertThrows(
                DALException.class,
                () -> sut.removeTrackFromPlaylist(0, 0)
        );
    }

    @Test
    void lookUpTrackFalse() throws SQLException {
        when(mockPrepStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.getInt(1)).thenReturn(0);

        boolean actual = sut.lookUpTrack(1);
        assertFalse(actual);
    }

    @Test
    void lookUpTrackTrue() throws SQLException {
        when(mockPrepStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(5);

        boolean actual = sut.lookUpTrack(1);
        assertTrue(actual);
    }

    @Test
    void lookUpTrackThrow() throws SQLException {
        when(mockPrepStatement.executeQuery()).thenThrow(SQLException.class);

        Assertions.assertThrows(
                DALException.class,
                () -> sut.lookUpTrack(0)
        );
    }
}