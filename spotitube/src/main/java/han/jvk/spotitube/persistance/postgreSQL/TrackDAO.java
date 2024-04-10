package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.persistance.ITrackDAO;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TrackDAO extends PostgresConnector implements ITrackDAO {
    @Override
    public List<TrackDTO> getAllTrackInPlaylist(int id) {
        final String query = "SELECT t.id, t.title, t.performer, t.duration, t.album, t.playcount, t.publication_date, t.description, t.offline_available\n" +
                "FROM tracksinplaylist tip LEFT JOIN public.tracks t on t.id = tip.track_id\n" +
                "WHERE playlist_id = ?;";
        List<TrackDTO> tracks = new ArrayList<>();

        try (Connection conn = connect()){
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                System.out.println("reached id: " + rs.getInt("id"));
                TrackDTO track = new TrackDTO(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("performer"),
                        rs.getInt("duration"),
                        rs.getString("album"),
                        rs.getInt("playcount"),
                        rs.getString("publication_date"),
                        rs.getString("description"),
                        rs.getBoolean("offline_available"));
                tracks.add(track);
            }
            return tracks;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
