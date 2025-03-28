package han.jvk.spotitube.remoteFacade.responses;

import han.jvk.spotitube.dto.TrackDTO;

import java.util.List;

public class TrackInPlaylistResponse {
    private List<TrackDTO> tracks;

    public TrackInPlaylistResponse(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }


}
