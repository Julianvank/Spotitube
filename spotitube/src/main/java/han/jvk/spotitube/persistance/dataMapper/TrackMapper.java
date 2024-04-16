package han.jvk.spotitube.persistance.dataMapper;

import han.jvk.spotitube.dto.TrackDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackMapper {
    public TrackDTO mapResultSetToTrackDTO(ResultSet rs) throws SQLException {
        TrackDTO track;
        track = new TrackDTO(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("performer"),
                rs.getInt("duration"),
                rs.getString("album"),
                rs.getInt("playcount"),
                rs.getString("publication_date"),
                rs.getString("description"),
                rs.getBoolean("offline_available")
        );
        return track;
    }
}
