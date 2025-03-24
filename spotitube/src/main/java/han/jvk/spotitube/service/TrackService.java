package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.ITrackDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.net.HttpURLConnection;
import java.util.List;

@ApplicationScoped
public class TrackService implements ITrackService {
    private static final Logger log = Logger.getLogger(TrackService.class.getName());

    ITrackDAO trackDAO;

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Override
    public List<TrackDTO> getAllTracksFromPlaylist(AuthenticatedUserDTO authUser, int id) {
        List<TrackDTO> list = trackDAO.getAllTracksInPlaylist(id);
        if (list.isEmpty()) log.info("Playlist contains no tracks: " + id);
        return list;
    }

    @Override
    public List<TrackDTO> getAvailableTracks(int id) {
        List<TrackDTO> list = trackDAO.getAvailableTracks(id);
        if (list.isEmpty()) log.info("No available tracks on: " + id);
        return list;
    }

    @Override
    public void addTrackToPlaylist(int id, TrackDTO trackDTO) throws ServiceException {
        try {
            if (!trackDAO.lookUpTrack(trackDTO.getId()))
                log.info("Track is not in database");
            trackDAO.addTrackToPlaylist(trackDTO.getId(), id);
        } catch (NoAffectedRowsException e) {
            log.info("There were no rows added to database");
        }
    }

    @Override
    public void removeTrackFromPlaylist(AuthenticatedUserDTO authUser, int playlistId, int trackId) {
//        try {
            trackDAO.removeTrackFromPlaylist(trackId, playlistId);
//        } catch (NoAffectedRowsException e) {
//            log.info("There was no track to delete");
//        }
    }
}
