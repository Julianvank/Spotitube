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
        return trackDAO.getAllTracksInPlaylist(id);
    }

    @Override
    public List<TrackDTO> getAvailableTracks(int id) {
        return trackDAO.getAvailableTracks(id);
    }

    @Override
    public void addTrackToPlaylist(int playlistId, TrackDTO track) throws ServiceException {
        if (!trackDAO.lookUpTrack(track.getId())) {
            return;
        }
        trackDAO.addTrackToPlaylist(track.getId(), playlistId);
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
