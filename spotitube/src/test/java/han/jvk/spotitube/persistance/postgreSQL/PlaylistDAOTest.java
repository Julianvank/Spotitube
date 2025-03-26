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
import static org.mockito.ArgumentMatchers.*;
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
        sut.setConnector(connector);

        MockitoAnnotations.openMocks(this);

        when(connector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPrepStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockPrepStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    }

}