package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.persistance.dataMapper.TrackMapper;
import han.jvk.spotitube.util.factory.DBConnection.IDBConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TrackDAOTest {
    @InjectMocks
    TrackDAO sut;
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Mock
    private TrackMapper mockTrackMapper;

    @Mock
    private IDBConnectionFactory connector;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(connector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        sut.setConnector(connector);
    }

    @Test
    void getAllTracksInPlaylistTest_NoErrorEncountered() throws SQLException, DALException {
        // Arrange
        int playlistId = 1;
        TrackDTO expectedTrack = new TrackDTO();
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockTrackMapper.mapResultSetToTrackDTO(mockResultSet)).thenReturn(expectedTrack);

        // Act
        List<TrackDTO> actualTracks = sut.getAllTracksInPlaylist(playlistId);

        // Assert
        assertEquals(1, actualTracks.size());
        assertEquals(expectedTrack, actualTracks.get(0));
        verify(mockPreparedStatement).setInt(1, playlistId);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    void getAllTracksInPlaylistTest_NoResults() throws SQLException, DALException {
        // Arrange
        int playlistId = 1;
        when(mockResultSet.next()).thenReturn(false);

        // Act
        List<TrackDTO> actualTracks = sut.getAllTracksInPlaylist(playlistId);

        // Assert
        assertTrue(actualTracks.isEmpty());
        verify(mockPreparedStatement).setInt(1, playlistId);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    void getAllTracksInPlaylistTest_EncounteredSqlException() throws SQLException {
        //Arrange
        int playlistId = 1;
        doThrow(new SQLException()).when(mockPreparedStatement).executeQuery();

        //Act & Assert
        assertThrows(DALException.class, () -> sut.getAllTracksInPlaylist(playlistId));
    }

    @Test
    void lookUpTrackTest_TrackInDatabase() throws SQLException, DALException {
        //Arrange
        when(mockResultSet.getInt(1)).thenReturn(1);

        //Act
        boolean actual = sut.lookUpTrack(1);

        //Arrange
        assertTrue(actual);
    }

    @Test
    void lookUpTrackTest_TrackNotInDatabase() throws SQLException, DALException {
        //Arrange
        when(mockResultSet.getInt(1)).thenReturn(0);

        //Act
        boolean actual = sut.lookUpTrack(1);

        //Arrange
        assertFalse(actual);
    }

    @Test
    void lookUpTrackTest_EncounteredSqlException() throws SQLException {
        //Arrange
        doThrow(new SQLException()).when(mockPreparedStatement).executeQuery();

        //Act
        assertThrows(DALException.class, () -> sut.lookUpTrack(1));
    }

}