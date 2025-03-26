package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.util.factory.DBConnection.IDBConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDAOTest {

    @InjectMocks
    UserDAO sut;

    @Mock
    Connection mockConnection;

    @Mock
    PreparedStatement mockStatement;

    @Mock
    ResultSet mockResultSet;

    @Mock
    IDBConnectionFactory connector;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(connector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

//        sut.setConnector(connector);
    }

    @Test
    void getPasswordByUserTest_NoErrorEncountered() throws SQLException {
        String username = "testUser";
        String expectedPassword = "testPassword";

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString(1)).thenReturn(expectedPassword);

        // Act
        String actualPassword = sut.getPasswordByUser(username);

        // Assert
        assertEquals(expectedPassword, actualPassword);
        verify(mockStatement).setString(1, username);
        verify(mockStatement).executeQuery();
    }

    @Test
    void getPasswordByUserFalse() throws SQLException {
        String username = "wrongUserName";
        String expectedPassword = "testPassword";

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString(1)).thenReturn(null);

        // Act
        String actualPassword = sut.getPasswordByUser(username);

        // Assert
        assertNotEquals(expectedPassword, actualPassword);
    }

    @Test
    public void testGetPasswordByUser_NoResult() throws Exception {
        // Arrange
        String username = "nonexistentUser";

        when(mockResultSet.next()).thenReturn(false);

        // Act
        String actualPassword = sut.getPasswordByUser(username);

        // Assert
        assertEquals("", actualPassword);
    }

    @Test
    public void testGetPasswordByUser_SQLException() throws Exception {
        when(mockStatement.executeQuery()).thenThrow(new SQLException());

        // No need for an assertion as the test expects an exception
        assertThrows(
                DALException.class,
                () -> sut.getPasswordByUser("testUser")
        );
    }
}