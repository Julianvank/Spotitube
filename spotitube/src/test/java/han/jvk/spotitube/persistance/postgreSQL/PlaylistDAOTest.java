package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.util.factory.DBConnection.IDBConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPrepStatement);
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
        int playlistId = 1;
        when(mockPrepStatement.executeUpdate()).thenReturn(0);
        when(mockPrepStatement.executeUpdate()).thenReturn(0);

        // Act & Assert
        assertThrows(
                NoAffectedRowsException.class,
                () -> sut.deletePlaylistById("testuser", playlistId)
        );
    }
    @Test
    void deletePlaylistByIdThrow() throws SQLException {
        when(mockPrepStatement.executeUpdate()).thenThrow(new SQLException());

        assertThrows(
                DALException.class,
                () -> sut.deletePlaylistById("owner", 0)
        );
    }

    @Test
    void addPlaylist() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPrepStatement);
        when(mockPrepStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockPrepStatement.executeUpdate()).thenReturn(0);

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setName("Test Playlist");

        assertThrows(
                NoAffectedRowsException.class,
                () -> sut.addPlaylist("testuser", playlistDTO)
        );
    }
    @Test
    void addPlaylistThrow() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPrepStatement);
        when(mockPrepStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockPrepStatement.executeUpdate()).thenThrow(SQLException.class);

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setName("Test Playlist");

        assertThrows(
                DALException.class,
                () -> sut.addPlaylist("testuser", playlistDTO)
        );
    }
    @Test
    void addPlaylistIndex() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPrepStatement);
        when(mockPrepStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockPrepStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(5);

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setName("Test Playlist");

        sut.addPlaylist("testuser", playlistDTO);

        assertEquals(5, playlistDTO.getId());
    }

    @Test
    void addPlaylistIndexFalse() throws SQLException {
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPrepStatement);
        when(mockPrepStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockPrepStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(false);
        when(mockResultSet.getInt(1)).thenReturn(5);

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setName("Test Playlist");


        assertThrows(
                DALException.class,
                () -> sut.addPlaylist("testuser", playlistDTO)
        );
    }

}