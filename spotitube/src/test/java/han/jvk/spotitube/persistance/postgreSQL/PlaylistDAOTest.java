package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.persistance.dataMapper.PlaylistMapper;
import han.jvk.spotitube.util.factory.DBConnection.IDBConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    @Mock
    PlaylistMapper mockPlaylistMapper;


    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(connector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPrepStatement);
//        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPrepStatement);
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(mockPrepStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockPrepStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    }

    @Test
    void getAllPlaylistTest_SuccessfulExecution() throws SQLException {
        //Arrange
        String input = "testOwner";
        when(mockPlaylistMapper.mapResultSetToPlaylistDTO(any())).thenReturn(new PlaylistDTO()).thenReturn(new PlaylistDTO());
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        //Act
        List<PlaylistDTO> actual = sut.getAllPlaylist(input);
        //Assert
        assertEquals(2, actual.size());
        verify(mockPrepStatement, times(1)).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockPlaylistMapper, times(2)).mapResultSetToPlaylistDTO(mockResultSet);
    }

    @Test
    void getAllPlaylistTest_SqlException() throws SQLException {
        //Arrange
        String input = "testOwner";
        when(mockPrepStatement.executeQuery()).thenThrow(new SQLException());

        //Act  Assert
        assertThrows(DALException.class, () -> sut.getAllPlaylist(input));
    }

    @Test
    void getPlaylistTest_SuccessfulExecution() throws SQLException {
        //Arrange
        String input = "testOwner";
        when(mockPlaylistMapper.mapResultSetToPlaylistDTO(any())).thenReturn(new PlaylistDTO());
        when(mockResultSet.next()).thenReturn(true);

        //Act
        PlaylistDTO actual = sut.getPlaylist(input, 1);

        //Assert
        assertNotNull(actual);
        verify(mockPrepStatement).setString(1, input);
        verify(mockPrepStatement).setString(2, "1");
    }

    @Test
    void getPlaylistTest_SQLException() throws SQLException {
        //Arrange
        String input = "testOwner";
        when(mockPrepStatement.executeQuery()).thenThrow(new SQLException());

        //Act & Assert
        assertThrows(DALException.class, () -> sut.getPlaylist(input, 1));
    }

    @Test
    void deletePlaylistByIdTest_SuccessfulExecution() throws SQLException {
        //Arrange
        String input = "testOwner";
        int id = 1;
        //Act
        sut.deletePlaylistById(input, id);

        //Assert
        verify(mockPrepStatement, times(2)).executeUpdate();
        verify(mockPrepStatement, times(2)).setInt(1, id);
    }

    @Test
    void deletePlaylistByIdTest_SQLException() throws SQLException {
        //Arrange
        String input = "testOwner";
        int id = 0;
        doThrow(new SQLException()).when(mockPrepStatement).executeUpdate();

        //Act & Assert
        assertThrows(DALException.class, () -> sut.deletePlaylistById(input, id));
    }

    @Test
    void addPlaylistTest_SuccessfulExecution() throws SQLException {
        //Arrange
        String input = "testOwner";
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setName("testPlaylist");
        when(mockPrepStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        //Act
        sut.addPlaylist(input, playlistDTO);

        //Arrange
        verify(mockPrepStatement).setString(1, playlistDTO.getName());
        verify(mockPrepStatement).setString(2, input);
        verify(mockPrepStatement).executeUpdate();
    }

    @Test
    void addPlayListTest_SqlException() throws SQLException {
        //Arrange
        String input = "testOwner";
        PlaylistDTO playlistDTO = new PlaylistDTO();
        doThrow(new SQLException()).when(mockPrepStatement).executeUpdate();

        //Arrange & Act
        assertThrows(DALException.class, () -> sut.addPlaylist(input, playlistDTO));
    }

}