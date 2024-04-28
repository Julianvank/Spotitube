package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.exception.ServiceException;

public interface IPlaylistService {
    PlaylistCollectionDTO getAllPlaylist(AuthenticatedUserDTO authUser) throws ServiceException;

    void deletePlaylistById(AuthenticatedUserDTO authUser, int id) throws ServiceException;

    void addPlaylist(AuthenticatedUserDTO authUser, PlaylistDTO playlistDTO) throws ServiceException;

    void editPlaylist(PlaylistDTO playlistDTO, int id) throws ServiceException;

    PlaylistDTO getPlaylist(AuthenticatedUserDTO authUser, int id) throws ServiceException;
}
