package han.jvk.spotitube.dto;

import java.util.ArrayList;
import java.util.List;

public class PlaylistCollectionDTO {
    List<PlaylistDTO> playlists;
    int length;

    public PlaylistCollectionDTO(List<PlaylistDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    public PlaylistCollectionDTO(){
        this.playlists = new ArrayList<>();
    }

    public List<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void putPlaylist(PlaylistDTO playlistDTO){
        playlists.add(playlistDTO);
    }
}
