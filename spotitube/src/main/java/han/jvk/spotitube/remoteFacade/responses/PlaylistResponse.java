package han.jvk.spotitube.remoteFacade.responses;

import han.jvk.spotitube.dto.PlaylistDTO;

import java.util.List;

public class PlaylistResponse {
    List<PlaylistDTO> playlists;
    int length;

    public PlaylistResponse(List<PlaylistDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }
}
