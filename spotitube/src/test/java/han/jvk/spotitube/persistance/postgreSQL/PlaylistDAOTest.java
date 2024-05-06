package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.util.factory.DBConnection.IDBConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
class PlaylistDAOTest {

    @InjectMocks
    PlaylistDAO sut;
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
        this.sut = new PlaylistDAO();
        sut.setConnector(connector);

        MockitoAnnotations.openMocks(this);

        when(connector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPrepStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockPrepStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    }

    @Test
    void GetAllPlaylist() throws SQLException, DALException {
        // Arrange
        String owner = "testowner";
        List<PlaylistDTO> expectedPlaylists = new ArrayList<>();
        expectedPlaylists.add(new PlaylistDTO());
        expectedPlaylists.add(new PlaylistDTO());

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(expectedPlaylists.get(0).getId(), expectedPlaylists.get(1).getId());

        // Act
        List<PlaylistDTO> actualPlaylists = sut.getAllPlaylist(owner);

        // Assert
        assertEquals(expectedPlaylists.size(), actualPlaylists.size());
    }

    @Test
    void getAllPlaylistThrows() throws SQLException {
        when(mockPrepStatement.executeQuery()).thenThrow(new SQLException());

        assertThrows(
                DALException.class,
                () -> sut.getAllPlaylist("owner")
        );
    }

    @Test
    void getPlaylist() throws SQLException {
        // Arrange
        String authUsername = "testusername";
        int playlistId = 1;
        PlaylistDTO expectedPlaylist = new PlaylistDTO(/* Fill with appropriate values */);

        when(mockResultSet.next()).thenReturn(true);
        // Mock retrieval of attributes like id, name, owner
        when(mockResultSet.getInt("id")).thenReturn(expectedPlaylist.getId());
        when(mockResultSet.getString("name")).thenReturn(expectedPlaylist.getName());
        when(mockResultSet.getString("owner")).thenReturn(expectedPlaylist.getOwner());

        // Act
        PlaylistDTO actualPlaylist = sut.getPlaylist(authUsername, playlistId);

        assertEquals(expectedPlaylist.getId(), actualPlaylist.getId());
        assertEquals(expectedPlaylist.getName(), actualPlaylist.getName());
        assertEquals(expectedPlaylist.getOwner(), actualPlaylist.getOwner());
    }

    @Test
    void deletePlaylistById() throws SQLException {
        when(mockPrepStatement.executeQuery()).thenThrow(new SQLException());

        assertThrows(
                DALException.class,
                () -> sut.getPlaylist("owner", 0)
        );
    }

    @Test
    void addPlaylist() {
    }

    @Test
    void addTracksToPlaylist() {
    }

    @Test
    void editPlaylist() {
    }
}