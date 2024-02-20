package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.persistance.IPlaylistDAO;
import jakarta.inject.Inject;

import java.util.List;

public class PlaylistService implements IPlaylistService {

    IPlaylistDAO playlistDAO;

    @Inject
    public void setPlaylistDAO(IPlaylistDAO playlistDAO){
        this.playlistDAO = playlistDAO;
    }

    @Override
    public PlaylistCollectionDTO getAllPlaylist(AuthenticatedUserDTO authUser) {
        PlaylistCollectionDTO collection = null;
        List<PlaylistDTO> list = playlistDAO.getAllPlaylist(authUser.getUsername());
        collection.setPlaylists(list);
        return collection;
    }

    @Override
    public void deletePlaylistById(AuthenticatedUserDTO authUser, int id) {
        playlistDAO.deletePlaylistById(authUser.getUsername(), id);
    }

    @Override
    public void addPlaylist(AuthenticatedUserDTO authUser, PlaylistDTO playlistDTO) {
        playlistDAO.addPlaylist(authUser.getUsername(), playlistDTO);
    }

    @Override
    public void editPlaylist(AuthenticatedUserDTO authUser, PlaylistDTO playlistDTO) {
        playlistDAO.editPlaylist(authUser.getUsername(), playlistDTO);
    }
}
