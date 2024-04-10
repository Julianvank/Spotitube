package han.jvk.spotitube.persistance;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;

import java.util.List;

public interface IPlaylistDAO {
    List<PlaylistDTO> getAllPlaylist(String username);

    void deletePlaylistById(String username, int id);

    void addPlaylist(String username, PlaylistDTO playlistDTO);

    void editPlaylist(String username, PlaylistDTO playlistDTO);

    PlaylistDTO getPlaylist(AuthenticatedUserDTO authUser, int id);

    void addTracksToPlaylist(String username, List<TrackDTO> tracks, int id);
}
