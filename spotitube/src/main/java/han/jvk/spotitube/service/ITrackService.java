package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.ServiceException;

import java.util.List;

public interface ITrackService {
    List<TrackDTO> getAllTracksFromPlaylist(AuthenticatedUserDTO authUser, int id);

    List<TrackDTO> getAvailableTracks(AuthenticatedUserDTO authUser, int id);

    void addTrackToPlaylist(int id, TrackDTO trackDTO) throws ServiceException;

    void removeTrackFromPlaylist(AuthenticatedUserDTO authUser, int id, int trackId);
}
