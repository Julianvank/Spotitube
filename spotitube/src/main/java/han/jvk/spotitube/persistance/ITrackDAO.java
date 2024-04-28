package han.jvk.spotitube.persistance;

import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.DALException;

import java.util.List;

public interface ITrackDAO {

    List<TrackDTO> getAllTrackInPlaylist(int id) throws DALException;


    List<TrackDTO> getAvailableTracks(int id) throws DALException;

    void addTrackToPlaylist(TrackDTO trackDTO, int id) throws DALException;

    void removeTrackFromPlaylist(int trackId, int id) throws DALException;

    boolean lookUpTrack(TrackDTO trackDTO) throws DALException;
}
