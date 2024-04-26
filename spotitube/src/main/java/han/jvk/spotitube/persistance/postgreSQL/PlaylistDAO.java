package han.jvk.spotitube.persistance.postgreSQL;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.persistance.IPlaylistDAO;
import han.jvk.spotitube.persistance.dataMapper.PlaylistMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PlaylistDAO extends PostgresConnector implements IPlaylistDAO {
    PlaylistMapper playlistMapper = new PlaylistMapper();


    @Override
    public List<PlaylistDTO> getAllPlaylist(String owner) {
        final String querie = "SELECT *\n" +
                "FROM playlists\n" +
                "WHERE owner =  (select id FROM users where username = ?)";

//        final String trackQuerie =
        List<PlaylistDTO> list = new ArrayList<>();

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(querie);

            stmt.setString(1, owner);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PlaylistDTO playlist = playlistMapper.mapResultSetToPlaylistDTO(rs);
                list.add(playlist);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public PlaylistDTO getPlaylist(AuthenticatedUserDTO authUser, int id) {
        final String querie = "SELECT id, name ((SELECT name FROM users WHERE name LIKE ?)) AS owner\n" +
                "FROM playlists\n" +
                "WHERE id = ?";

        PlaylistDTO playlist = new PlaylistDTO();
        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(querie);

            stmt.setString(1, authUser.getUsername());
            stmt.setString(2, String.valueOf(id));

            ResultSet rs = stmt.executeQuery(querie);


            while (rs.next()) {
                playlist = playlistMapper.mapResultSetToPlaylistDTO(rs);
            }

            return playlist;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlist;
    }

    @Override
    public void deletePlaylistById(String username, int id) {
        final String query1 = "DELETE FROM tracksInPlaylist\n" +
                "WHERE PLAYLIST_ID = ?";
        final String query2 = "DELETE FROM playlists\n" +
                "WHERE ID = ?";

        try (Connection conn = connect()) {
            PreparedStatement stmt1 = conn.prepareStatement(query1);

            stmt1.setInt(1, id);

            stmt1.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }


        try (Connection conn = connect()) {
            PreparedStatement stmt2 = conn.prepareStatement(query2);

            stmt2.setInt(1, id);

            stmt2.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addPlaylist(String username, PlaylistDTO playlistDTO) {
        final String playlistQuery = "INSERT INTO playlists (NAME, OWNER)\n" +
                "VALUES (?, (SELECT id from users where username = ?));";


        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(playlistQuery, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, playlistDTO.getName());
            stmt.setString(2, username);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating playlist failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    playlistDTO.setId(id);
                } else {
                    throw new SQLException("Creating playlist failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTracksToPlaylist(String username, List<TrackDTO> tracks, int playlistId) {
        final String trackQuery = "INSERT INTO tracksInPlaylist (TRACK_ID, PLAYLIST_ID)\n" +
                "VALUES (?, ?);";
        try (Connection conn = connect()) {
            for(TrackDTO track : tracks){
                PreparedStatement stmt = conn.prepareStatement(trackQuery);

                stmt.setInt(1, track.getId());
                stmt.setInt(2, playlistId);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editPlaylist(PlaylistDTO playlistDTO, int id) {
        final String query = "UPDATE playlists SET name = ? WHERE ID = id";

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, playlistDTO.getName());

            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
