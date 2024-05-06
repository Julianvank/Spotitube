package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.ITrackDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.net.HttpURLConnection;
import java.util.List;

@ApplicationScoped
public class TrackService implements ITrackService {

    ITrackDAO trackDAO;

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Override
    public List<TrackDTO> getAllTracksFromPlaylist(AuthenticatedUserDTO authUser, int id) {
        List<TrackDTO> list = trackDAO.getAllTrackInPlaylist(id);
        if (list.isEmpty()) throw new ServiceException("Playlist is empty", HttpURLConnection.HTTP_OK);
        return list;
    }

    @Override
    public List<TrackDTO> getAvailableTracks(int id) {
        List<TrackDTO> list = trackDAO.getAvailableTracks(id);
        if (list.isEmpty()) throw new ServiceException("There are no available tracks.", HttpURLConnection.HTTP_OK);
        return list;
    }

    @Override
    public void addTrackToPlaylist(int id, TrackDTO trackDTO) throws ServiceException {
        try {
            if (!trackDAO.lookUpTrack(trackDTO.getId()))
                throw new ServiceException("Track is not in database", HttpURLConnection.HTTP_CONFLICT);
            trackDAO.addTrackToPlaylist(trackDTO.getId(), id);
        } catch (NoAffectedRowsException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeTrackFromPlaylist(AuthenticatedUserDTO authUser, int id, int trackId) {
        try {
            trackDAO.removeTrackFromPlaylist(trackId, id);
        } catch (NoAffectedRowsException e) {
            throw new ServiceException(e);
        }
    }
}
