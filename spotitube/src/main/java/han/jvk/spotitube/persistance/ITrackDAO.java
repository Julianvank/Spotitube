package han.jvk.spotitube.persistance;

import han.jvk.spotitube.dto.TrackDTO;

import java.util.List;

public interface ITrackDAO {

    List<TrackDTO> getAllTrackInPlaylist(int id);


    List<TrackDTO> getAvailableTracks(int id);

    void addTrackToPlaylist(TrackDTO trackDTO, int id);

    void removeTrackFromPlaylist(int trackId, int id);

    boolean lookUpTrack(TrackDTO trackDTO);
}
