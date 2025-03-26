package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.persistance.ITokenDAO;
import han.jvk.spotitube.persistance.dataMapper.PlaylistMapper;
import han.jvk.spotitube.util.factory.DBConnection.IDBConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TokenDAOTest {

    @InjectMocks
    TokenDAO sut;
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
        MockitoAnnotations.openMocks(this);

        when(connector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPrepStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockPrepStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    }

    @Test
    void findUserByTokenTest_Success() throws SQLException {
        //Arrange
        String token = "token";
        String expected = "user";
        when(mockResultSet.getString(1)).thenReturn(expected);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        //Act
        String actual = sut.findUserByToken(token);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void findUserByTokenTest_NotFound() throws SQLException {
        //Arrange
        String wrongToken = "wrongToken";

        when(mockResultSet.next()).thenReturn(false);

        //Act
        String actual = sut.findUserByToken(wrongToken);

        //Assert
        assertNull(actual);
    }

    @Test
    void findUserByTokenTest_SqlException() throws SQLException {
        //Arrange
        String token = "token";
        doThrow(new SQLException()).when(mockPrepStatement).executeQuery();

        //Act & Assert
        assertThrows(DALException.class, () -> sut.findUserByToken(token));
    }

    @Test
    void saveAuthenticatedUserTest_Success() throws SQLException {
        //Arrange
        AuthenticatedUserDTO authUser = new AuthenticatedUserDTO("username", "token");

        //Act
        sut.saveAuthenticatedUser(authUser);

        //Assert
        verify(mockPrepStatement, times(1)).executeUpdate();
        verify(mockPrepStatement, times(1)).setString(1, authUser.getToken());
        verify(mockPrepStatement, times(1)).setString(2, authUser.getUsername());
    }

    @Test
    void saveAuthenticatedUserTest_SqlException() throws SQLException {
        //Arrange
        AuthenticatedUserDTO authUser = new AuthenticatedUserDTO("username", "token");
        doThrow(new SQLException()).when(mockPrepStatement).executeUpdate();

        //Act & Assert
        assertThrows(DALException.class, () -> sut.saveAuthenticatedUser(authUser));

    }
}