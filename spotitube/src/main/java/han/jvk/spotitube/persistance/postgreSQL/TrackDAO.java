package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.persistance.DatabaseConnector;
import han.jvk.spotitube.persistance.ITrackDAO;
import han.jvk.spotitube.persistance.dataMapper.TrackMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TrackDAO extends DatabaseConnector implements ITrackDAO {

    TrackMapper trackMapper = new TrackMapper();


    @Override
    public List<TrackDTO> getAllTracksInPlaylist(int id) throws DALException {
        final String query = "SELECT t.id, t.title, t.performer, t.duration, t.album, t.playcount, t.publication_date, t.description, t.offline_available\n" +
                "FROM tracksinplaylist tip LEFT JOIN public.tracks t on t.id = tip.track_id\n" +
                "WHERE playlist_id = ?;";
        return getTrackDTOS(id, query);
    }

    @Override
    public List<TrackDTO> getAvailableTracks(int id) throws DALException {
        final String query = "SELECT t.id, t.title, t.performer, t.duration, t.album, t.playcount, t.publication_date, t.description, t.offline_available " +
                "FROM tracks t RIGHT OUTER JOIN tracksinplaylist tip on t.id = tip.track_id where tip.playlist_id != ?";
        return getTrackDTOS(id, query);
    }

    private List<TrackDTO> getTrackDTOS(int id, String query) {
        List<TrackDTO> tracks = new ArrayList<>();

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TrackDTO track = trackMapper.mapResultSetToTrackDTO(rs);
                tracks.add(track);
            }
            return tracks;

        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
    }

    @Override
    public void addTrackToPlaylist(int trackId, int playlistId) throws DALException {
        final String query = "INSERT INTO tracksInPlaylist (TRACK_ID, PLAYLIST_ID)\n" +
                "VALUES (?, ?)";

        executeQuery(trackId, playlistId, query);
    }

    @Override
    public void removeTrackFromPlaylist(int trackId, int playlistId) throws DALException {
        final String query = "DELETE FROM tracksInPlaylist WHERE TRACK_ID = ? AND PLAYLIST_ID = ?";

        executeQuery(trackId, playlistId, query);
    }

    private void executeQuery(int trackId, int playlistId, String query) {
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, trackId);
            stmt.setInt(2, playlistId);


            int affectedRows = stmt.executeUpdate();
//            if (affectedRows == 0) throw new NoAffectedRowsException("No rows were affected.", 200);

        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
    }

    @Override
    public boolean lookUpTrack(int trackId) throws DALException {
        final String query = "SELECT COUNT(ID) FROM tracks WHERE ID = ?";

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, trackId);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                if (rs.getInt(1) > 0) {
                    return true;
                }
            }

        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
        return false;
    }
}
