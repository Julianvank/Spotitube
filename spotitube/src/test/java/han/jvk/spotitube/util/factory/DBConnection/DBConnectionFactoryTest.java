package han.jvk.spotitube.util.factory.DBConnection;

import han.jvk.spotitube.exception.UtilException;
import han.jvk.spotitube.util.DBPropertiesReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class DBConnectionFactoryTest {

    @Mock
    DBPropertiesReader propertiesReaderMock;

    @Mock
    Connection connectionMock;

    @Mock
    DriverManager driverManager;

    @InjectMocks
    DBConnectionFactory sut;

    @BeforeEach
    void setUp() {
        this.sut = new DBConnectionFactory();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void setPropertiesNullName() {
        Assertions.assertThrows(
                UtilException.class,
                () -> sut.setProperties(null)
        );
    }
}