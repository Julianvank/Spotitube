package han.jvk.spotitube.service;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.persistance.IPlaylistDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.net.HttpURLConnection;
import java.util.List;

@ApplicationScoped
public class PlaylistService implements IPlaylistService {

    IPlaylistDAO playlistDAO;
    ITrackService trackService;

    @Inject
    public void setPlaylistDAO(IPlaylistDAO playlistDAO){
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTrackService(ITrackService trackService){this.trackService = trackService;}

    @Override
    public PlaylistCollectionDTO getAllPlaylist(AuthenticatedUserDTO authUser) throws ServiceException {
        PlaylistCollectionDTO collection = new PlaylistCollectionDTO();
        List<PlaylistDTO> list;
        list = playlistDAO.getAllPlaylist(authUser.getUsername());

        if(list.isEmpty()){
            throw new ServiceException("There are no playlist", HttpURLConnection.HTTP_NO_CONTENT);
        }

        addAllTracksToList(authUser, list, collection);
        collection.setLength(getLength(list));


        return collection;
    }

    private void addAllTracksToList(AuthenticatedUserDTO authUser, List<PlaylistDTO> list, PlaylistCollectionDTO collection) {
        for(PlaylistDTO playlist : list){
            playlist.setTracks(trackService.getAllTracksFromPlaylist(authUser, playlist.getId()));
        }

        collection.setPlaylists(list);
    }

    @Override
    public PlaylistDTO getPlaylist(AuthenticatedUserDTO authUser, int id) {
        return playlistDAO.getPlaylist(authUser, id);
    }

    @Override
    public void deletePlaylistById(AuthenticatedUserDTO authUser, int id) {
        playlistDAO.deletePlaylistById(authUser.getUsername(), id);
    }

    @Override
    public void addPlaylist(AuthenticatedUserDTO authUser, PlaylistDTO playlistDTO) {
        playlistDAO.addPlaylist(authUser.getUsername(), playlistDTO);
        playlistDAO.addTracksToPlaylist(authUser.getUsername(), playlistDTO.getTracks(), playlistDTO.getId());
    }

    @Override
    public void editPlaylist(PlaylistDTO playlistDTO, int id) {
        playlistDAO.editPlaylist(playlistDTO, id);
    }

    private int getLength(List<PlaylistDTO> playlists){
        int length = 0;
        for(PlaylistDTO playlist : playlists){
            for (TrackDTO track : playlist.getTracks()){
                length += track.getDuration();
            }
        }
        return length;
    }
}
