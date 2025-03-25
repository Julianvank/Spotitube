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
import org.jboss.logging.Logger;

import java.net.HttpURLConnection;
import java.util.List;

@ApplicationScoped
public class PlaylistService implements IPlaylistService {

    IPlaylistDAO playlistDAO;
    ITrackService trackService;
    private static final Logger log = Logger.getLogger(PlaylistService.class.getName());

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

        if (list.isEmpty()) log.info("There are no playlist");

        collection.setPlaylists(addAllTracksToList(authUser, list));
        collection.setLength(calculateLength(collection.getPlaylists()));

        return collection;
    }

    private List<PlaylistDTO> addAllTracksToList(AuthenticatedUserDTO authUser, List<PlaylistDTO> list) {
        for (PlaylistDTO playlist : list) {
            playlist.setTracks(trackService.getAllTracksFromPlaylist(authUser, playlist.getId()));
        }

        return list;
    }

    @Override
    public PlaylistDTO getPlaylist(AuthenticatedUserDTO authUser, int id) throws ServiceException {
        PlaylistDTO playlist = playlistDAO.getPlaylist(authUser.getUsername(), id);
        if (playlist == null) log.info("There is no playlist");

        return playlist;
    }

    @Override
    public void deletePlaylistById(AuthenticatedUserDTO authUser, int id) throws ServiceException {
        try {
            playlistDAO.deletePlaylistById(authUser.getUsername(), id);
        } catch (NoAffectedRowsException e) {
            log.info("There were no rows to remove");
            throw new ServiceException("Encountered exception: " + e.getMessage(), 204);
        }
    }

    @Override
    public void addPlaylist(AuthenticatedUserDTO authUser, PlaylistDTO playlistDTO) throws ServiceException {
        if (playlistDTO.getId() != -1)
            log.info("Incorrect id of playlist");

        try {
            playlistDAO.addPlaylist(authUser.getUsername(), playlistDTO);
            playlistDAO.addTracksToPlaylist(authUser.getUsername(), playlistDTO.getTracks(), playlistDTO.getId());
        } catch (NoAffectedRowsException e) {
            log.info("There were no rows to add");
        }
    }

    @Override
    public void editPlaylist(PlaylistDTO playlistDTO, int id) throws ServiceException {
        try {
            playlistDAO.editPlaylist(playlistDTO, id);
        } catch (NoAffectedRowsException e) {
            log.info("There were no rows to edit");
        }
    }

    private int calculateLength(List<PlaylistDTO> playlists) {
        int length = 0;
        for (PlaylistDTO playlist : playlists) {
            for (TrackDTO track : playlist.getTracks()) {
                length += track.getDuration();
            }
        }
        return length;
    }
}
