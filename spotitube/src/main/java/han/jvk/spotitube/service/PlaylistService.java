package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.IPlaylistDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.net.HttpURLConnection;
import java.util.List;

@ApplicationScoped
public class PlaylistService implements IPlaylistService {


    IPlaylistDAO playlistDAO;
    ITrackService trackService;

    @Inject
    public void setPlaylistDAO(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTrackService(ITrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public PlaylistCollectionDTO getAllPlaylist(AuthenticatedUserDTO authUser) throws ServiceException {
        PlaylistCollectionDTO collection = new PlaylistCollectionDTO();
        List<PlaylistDTO> list = playlistDAO.getAllPlaylist(authUser.getUsername());

        if (list.isEmpty())
            throw new ServiceException("There are no playlist", HttpURLConnection.HTTP_CONFLICT);

        addAllTracksToList(authUser, list, collection);
        collection.setLength(getLength(list));

        return collection;
    }

    private void addAllTracksToList(AuthenticatedUserDTO authUser, List<PlaylistDTO> list, PlaylistCollectionDTO collection) {
        if (list.isEmpty()) throw new ServiceException("No tracks to add", HttpURLConnection.HTTP_CONFLICT);

        for (PlaylistDTO playlist : list) {
            playlist.setTracks(trackService.getAllTracksFromPlaylist(authUser, playlist.getId()));
        }

        collection.setPlaylists(list);
    }

    @Override
    public PlaylistDTO getPlaylist(AuthenticatedUserDTO authUser, int id) throws ServiceException {
        PlaylistDTO playlist = playlistDAO.getPlaylist(authUser, id);
        if (playlist == null) throw new ServiceException("There is no playlist", HttpURLConnection.HTTP_CONFLICT);

        return playlist;
    }

    @Override
    public void deletePlaylistById(AuthenticatedUserDTO authUser, int id) throws ServiceException {
        try {
            playlistDAO.deletePlaylistById(authUser.getUsername(), id);
        } catch (NoAffectedRowsException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addPlaylist(AuthenticatedUserDTO authUser, PlaylistDTO playlistDTO) throws ServiceException {
        if (playlistDTO.getId() != -1)
            throw new ServiceException("Incorrect id of playlist", HttpURLConnection.HTTP_CONFLICT);

        try {
            playlistDAO.addPlaylist(authUser.getUsername(), playlistDTO);
            playlistDAO.addTracksToPlaylist(authUser.getUsername(), playlistDTO.getTracks(), playlistDTO.getId());
        } catch (NoAffectedRowsException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void editPlaylist(PlaylistDTO playlistDTO, int id) throws ServiceException {
        try {
            playlistDAO.editPlaylist(playlistDTO, id);
        } catch (NoAffectedRowsException e) {
            throw new ServiceException(e);
        }
    }

    private int getLength(List<PlaylistDTO> playlists) {
        int length = 0;
        for (PlaylistDTO playlist : playlists) {
            for (TrackDTO track : playlist.getTracks()) {
                length += track.getDuration();
            }
        }
        return length;
    }
}
