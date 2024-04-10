package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.persistance.ITrackDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TrackService implements ITrackService{

    ITrackDAO trackDAO;

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO){this.trackDAO = trackDAO;}

    @Override
    public List<TrackDTO> getAllTracksFromPlaylist(AuthenticatedUserDTO authUser, int id) {
        return trackDAO.getAllTrackInPlaylist(id);
    }

    @Override
    public void getAvailableTracks(AuthenticatedUserDTO authUser, int id) {

    }

    @Override
    public void addTrackToPlaylist(AuthenticatedUserDTO authUser, int id, TrackDTO trackDTO) {

    }

    @Override
    public void removeTrackFromPlaylist(AuthenticatedUserDTO authUser, int id, int trackId) {

    }
}
