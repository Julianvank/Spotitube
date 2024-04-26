package han.jvk.spotitube.dto;

import java.util.HashMap;
import java.util.List;

public class HealthDTO {

    private List<TrackDTO> tracks;

    public HealthDTO(){};

    public HealthDTO(List<TrackDTO> tracks){
        this.tracks = tracks;
    }


    public List<TrackDTO> getTracks(){
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "HealthDTO{" +
                "tracks=" + tracks +
                '}';
    }
}
