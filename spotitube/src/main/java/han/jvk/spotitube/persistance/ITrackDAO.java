package han.jvk.spotitube.persistance;

import han.jvk.spotitube.dto.TrackDTO;

import java.util.List;

public interface ITrackDAO {

    List<TrackDTO> getAllTrackInPlaylist(int id);
}
