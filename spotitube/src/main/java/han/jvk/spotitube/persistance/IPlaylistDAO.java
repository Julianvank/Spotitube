package han.jvk.spotitube.persistance;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.DALException;

import java.util.List;

public interface IPlaylistDAO{
    List<PlaylistDTO> getAllPlaylist(String username) throws DALException;

    void deletePlaylistById(String username, int id) throws DALException;

    void addPlaylist(String username, PlaylistDTO playlistDTO) throws DALException;

    void editPlaylist(PlaylistDTO playlistDTO, int id) throws DALException;

    PlaylistDTO getPlaylist(AuthenticatedUserDTO authUser, int id) throws DALException;

    void addTracksToPlaylist(String username, List<TrackDTO> tracks, int id) throws DALException;
}
