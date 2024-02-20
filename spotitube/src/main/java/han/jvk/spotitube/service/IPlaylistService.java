package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;

import java.util.List;

public interface IPlaylistService {
    PlaylistCollectionDTO getAllPlaylist(AuthenticatedUserDTO authUser);

    void deletePlaylistById(AuthenticatedUserDTO authUser, int id);

    void addPlaylist(AuthenticatedUserDTO authUser, PlaylistDTO playlistDTO);

    void editPlaylist(AuthenticatedUserDTO authUser, PlaylistDTO playlistDTO);
}
