package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.exception.ServiceException;
import han.jvk.spotitube.persistance.IPlaylistDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PlaylistServiceTest {

    @Mock
    IPlaylistDAO playlistDAOMock;
    IPlaylistService sut;
    AuthenticatedUserDTO authUser = new AuthenticatedUserDTO("username", "token");

    @BeforeEach
    void setUp() {
        sut = new PlaylistService();

        openMocks(this);
    }

    @Test
    void getAllPlaylist() {
    }

    @Test
    void getAllPlaylistThrow() {
        when(playlistDAOMock.getAllPlaylist(anyString())).thenReturn(null);

        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(
                ServiceException.class,
                () -> sut.getAllPlaylist(authUser)
        );
    }

    @Test
    void getPlaylist() {
    }

    @Test
    void deletePlaylistById() {
    }

    @Test
    void addPlaylist() {
    }

    @Test
    void editPlaylist() {
    }
}