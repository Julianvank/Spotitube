package han.jvk.spotitube.dto;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDTO {

    private int id;
    private String name;
    private String owner;
    private List<TrackDTO> tracks;

    public PlaylistDTO(int id, String name, String owner) {
        tracks = new ArrayList<>();
        this.name = name;
        this.owner = owner;
        this.id = id;
    }

    public PlaylistDTO(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void putTrack(TrackDTO trackDTO){
        tracks.add(trackDTO);
    }

    public List<TrackDTO> getTracks(){
        return tracks;
    }

    public void setTrack(List<TrackDTO> list){
        this.tracks = list;
    }

}
