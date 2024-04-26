package han.jvk.spotitube.persistance.dataMapper;

import han.jvk.spotitube.dto.PlaylistDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistMapper {
    public PlaylistDTO mapResultSetToPlaylistDTO(ResultSet rs) throws SQLException {
        PlaylistDTO playlist;
        playlist = new PlaylistDTO(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("owner"),
                new ArrayList<>()
        );
        return playlist;
    }
}
