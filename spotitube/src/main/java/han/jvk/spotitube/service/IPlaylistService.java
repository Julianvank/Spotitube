package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.exception.ServiceException;

import java.util.List;

public interface IPlaylistService {
    PlaylistCollectionDTO getAllPlaylist(AuthenticatedUserDTO authUser) throws ServiceException;

    void deletePlaylistById(AuthenticatedUserDTO authUser, int id);

    void addPlaylist(AuthenticatedUserDTO authUser, PlaylistDTO playlistDTO);

    void editPlaylist(AuthenticatedUserDTO authUser, PlaylistDTO playlistDTO);

    PlaylistDTO getPlaylist(AuthenticatedUserDTO authUser, int id);
}
