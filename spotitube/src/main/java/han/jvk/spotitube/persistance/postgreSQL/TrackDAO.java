package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.persistance.ITrackDAO;
import han.jvk.spotitube.persistance.dataMapper.TrackMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TrackDAO extends PostgresConnector implements ITrackDAO {

    TrackMapper trackMapper = new TrackMapper();
    

    @Override
    public List<TrackDTO> getAllTrackInPlaylist(int id) throws DALException {
        final String query = "SELECT t.id, t.title, t.performer, t.duration, t.album, t.playcount, t.publication_date, t.description, t.offline_available\n" +
                "FROM tracksinplaylist tip LEFT JOIN public.tracks t on t.id = tip.track_id\n" +
                "WHERE playlist_id = ?;";
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
    public List<TrackDTO> getAvailableTracks(int id) throws DALException {
        final String query = "SELECT t.id, t.title, t.performer, t.duration, t.album, t.playcount, t.publication_date, t.description, t.offline_available\n" +
                "FROM tracks t RIGHT OUTER JOIN tracksinplaylist tip on t.id = tip.track_id where available = true";
        List<TrackDTO> tracks = new ArrayList<>();

        try (Connection conn = connect()) {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

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
    public void addTrackToPlaylist(TrackDTO trackDTO, int id) throws DALException {
        final String query = "INSERT INTO tracksInPlaylist (TRACK_ID, PLAYLIST_ID)\n" +
                "VALUES (?, ?)";

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, trackDTO.getId());
            stmt.setInt(2, id);


            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
    }

    @Override
    public void removeTrackFromPlaylist(int trackId, int id) throws DALException {
        final String query = "DELETE FROM tracksInPlaylist WHERE PLAYLIST_ID = ? AND TRACK_ID = ?";

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);
            stmt.setInt(2, trackId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
    }

    @Override
    public boolean lookUpTrack(TrackDTO trackDTO) throws DALException {
        final String query = "SELECT COUNT(ID) FROM tracks WHERE ID = ?";

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, trackDTO.getId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if(rs.getInt(1) > 0){
                    return true;
                }
            }


        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
        return false;
    }
}
