package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.persistance.DatabaseConnector;
import han.jvk.spotitube.persistance.ITrackDAO;
import han.jvk.spotitube.persistance.dataMapper.TrackMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TrackDAO extends DatabaseConnector implements ITrackDAO {

    TrackMapper trackMapper = new TrackMapper();


    @Override
    public List<TrackDTO> getAllTracksInPlaylist(int id) throws DALException {
        return getTrackListFromDatabaseById(id, GET_ALL_TRACKS_IN_PLAYLIST_QUERY);
    }

    @Override
    public List<TrackDTO> getAvailableTracks(int id) throws DALException {
        return getTrackListFromDatabaseById(id, GET_AVAILABLE_TRACKS_QUERY);
    }

    @Override
    public void addTrackToPlaylist(int trackId, int playlistId) throws DALException {
        ExecuteQueryTrackInPlaylistByIds(trackId, playlistId, INSERT_TRACK_IN_PLAYLIST_QUERY);
    }

    @Override
    public void removeTrackFromPlaylist(int trackId, int playlistId) throws DALException {
        ExecuteQueryTrackInPlaylistByIds(trackId, playlistId, DELETE_TRACK_FROM_PLAYLIST_QUERY);
    }

    @Override
    public boolean lookUpTrack(int trackId) throws DALException {
        boolean result = false;
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(LOOKUP_TRACK_QUERY);

            stmt.setInt(1, trackId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int amountFound = rs.getInt(1);
                    if (amountFound > 0) {
                        result = true;
                    }

                }
            }

        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.");
        }
        return result;
    }

    private List<TrackDTO> getTrackListFromDatabaseById(int id, String query) {
        List<TrackDTO> tracks = new ArrayList<>();

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TrackDTO track = trackMapper.mapResultSetToTrackDTO(rs);
                    tracks.add(track);
                }
            }
        } catch (SQLException e) {
            throw new DALException("Failed to retrieve tracks for playlist ID: " + id);
        }
        return tracks;
    }

    private void ExecuteQueryTrackInPlaylistByIds(int trackId, int playlistId, String query) {
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, trackId);
            stmt.setInt(2, playlistId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.");
        }
    }

    private static final String GET_ALL_TRACKS_IN_PLAYLIST_QUERY = "SELECT t.id, t.title, t.performer, t.duration, t.album, t.playcount, t.publication_date, t.description, t.offline_available " +
            "FROM tracksinplaylist tip LEFT JOIN public.tracks t on t.id = tip.track_id " +
            "WHERE playlist_id = ?;";

    private static final String GET_AVAILABLE_TRACKS_QUERY = "SELECT t.id, t.title, t.performer, t.duration, t.album, t.playcount, t.publication_date, t.description, t.offline_available " +
            "FROM tracks t RIGHT OUTER JOIN tracksinplaylist tip on t.id = tip.track_id " +
            "WHERE tip.playlist_id != ?";

    private static final String INSERT_TRACK_IN_PLAYLIST_QUERY = "INSERT INTO tracksInPlaylist (TRACK_ID, PLAYLIST_ID) VALUES (?, ?)";
    private static final String DELETE_TRACK_FROM_PLAYLIST_QUERY = "DELETE FROM tracksInPlaylist WHERE TRACK_ID = ? AND PLAYLIST_ID = ?";

    private static final String LOOKUP_TRACK_QUERY = "SELECT COUNT(ID) FROM tracks WHERE ID = ?";

}
