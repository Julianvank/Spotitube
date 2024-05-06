package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.DALException;
import han.jvk.spotitube.exception.NoAffectedRowsException;
import han.jvk.spotitube.persistance.DatabaseConnector;
import han.jvk.spotitube.persistance.IPlaylistDAO;
import han.jvk.spotitube.persistance.dataMapper.PlaylistMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PlaylistDAO extends DatabaseConnector implements IPlaylistDAO {
    PlaylistMapper playlistMapper = new PlaylistMapper();

    @Override
    public List<PlaylistDTO> getAllPlaylist(String owner) throws DALException {
        final String query = "SELECT *\n" +
                "FROM playlists\n" +
                "WHERE owner =  (select id FROM users where username = ?)";

        List<PlaylistDTO> list = new ArrayList<>();

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, owner);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PlaylistDTO playlist = playlistMapper.mapResultSetToPlaylistDTO(rs);
                list.add(playlist);
            }

            return list;
        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e, HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }

    @Override
    public PlaylistDTO getPlaylist(String authUsername, int id) throws DALException {
        final String query = "SELECT id, name, ((SELECT name FROM users WHERE name LIKE ?)) AS owner\n" +
                "FROM playlists\n" +
                "WHERE id = ?";

        PlaylistDTO playlist = null;
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, authUsername);
            stmt.setString(2, String.valueOf(id));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                playlist = playlistMapper.mapResultSetToPlaylistDTO(rs);
            }

            return playlist;
        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
    }

    @Override
    public void deletePlaylistById(String username, int id) throws DALException {
        final String query1 = "DELETE FROM tracksInPlaylist\n" +
                "WHERE PLAYLIST_ID = ?";
        final String query2 = "DELETE FROM playlists\n" +
                "WHERE ID = ?";

        try (Connection conn = connect()) {
            PreparedStatement stmt1 = conn.prepareStatement(query1);
            stmt1.setInt(1, id);
            int affectedRows1 = stmt1.executeUpdate();

            PreparedStatement stmt2 = conn.prepareStatement(query2);
            stmt2.setInt(1, id);
            int affectedRows2 = stmt2.executeUpdate();
            if (affectedRows1 == 0 || affectedRows2 == 0) throw new NoAffectedRowsException("No rows were affected.", 200);

        } catch (SQLException e){
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
    }

    @Override
    public void addPlaylist(String username, PlaylistDTO playlistDTO) throws DALException {
        final String playlistQuery = "INSERT INTO playlists (NAME, OWNER)\n" +
                "VALUES (?, (SELECT id from users where username = ?));";


        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(playlistQuery, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, playlistDTO.getName());
            stmt.setString(2, username);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) throw new NoAffectedRowsException("No rows were affected.", HttpURLConnection.HTTP_OK);

            assignId(playlistDTO, stmt);

        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
    }

    private static void assignId(PlaylistDTO playlistDTO, PreparedStatement stmt) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                playlistDTO.setId(id);
            } else {
                throw new SQLException("No ID obtained.");
            }
        }
    }

    @Override
    public void addTracksToPlaylist(String username, List<TrackDTO> tracks, int playlistId) throws DALException {
        final String trackQuery = "INSERT INTO tracksInPlaylist (TRACK_ID, PLAYLIST_ID)\n" +
                "VALUES (?, ?);";
        try (Connection conn = connect()) {
            for(TrackDTO track : tracks){
                PreparedStatement stmt = conn.prepareStatement(trackQuery);

                stmt.setInt(1, track.getId());
                stmt.setInt(2, playlistId);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) throw new NoAffectedRowsException("No rows were affected.", 200);

            }
        } catch (SQLException e) {
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }
    }

    @Override
    public void editPlaylist(PlaylistDTO playlistDTO, int id) throws DALException {
        final String query = "UPDATE playlists SET name = ? WHERE ID = ?";

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, playlistDTO.getName());
            stmt.setInt(2, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) throw new NoAffectedRowsException("No rows were affected.", 200);

        } catch (SQLException e){
            throw new DALException("A problem was found while fulfilling the database request.", e);
        }

    }
}
