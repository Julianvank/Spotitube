package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistCollectionDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.APIException;
import han.jvk.spotitube.remoteFacade.responses.TrackInPlaylistResponse;
import han.jvk.spotitube.service.IPlaylistService;
import han.jvk.spotitube.service.ITrackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/playlists/")
public class PlaylistResource extends TokenRequiredResource {

    private IPlaylistService playlistService;
    private ITrackService trackService;

    @Inject
    public void setPlaylistService(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(ITrackService trackService) {
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylist(@QueryParam("token") final String token) throws APIException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        return getAllPlaylistResponse(authUser);
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") final String token, @PathParam("id") final int id) throws APIException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        playlistService.deletePlaylistById(authUser, id);

        return getAllPlaylistResponse(authUser);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") final String token, PlaylistDTO playlistDTO) {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        playlistService.addPlaylist(authUser, playlistDTO);

        return getAllPlaylistResponse(authUser);
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") final String token, @PathParam("id") final int id, PlaylistDTO playlistDTO) throws APIException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        playlistService.editPlaylist(playlistDTO, id);

        return getAllPlaylistResponse(authUser);
    }


    @Path("/{id}/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksFromPlaylist(@QueryParam("token") final String token, @PathParam("id") final int id) throws APIException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        return getAllTracksResponse(id, authUser);
    }


    @Path("/{id}/tracks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") final String token, @PathParam("id") final int playlistId, TrackDTO trackDTO) throws APIException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        trackService.addTrackToPlaylist(playlistId, trackDTO);

        return getAllTracksResponse(playlistId, authUser);
    }

    @Path("/{id}/tracks/{trackId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@QueryParam("token") final String token, @PathParam("id") final int playlistId, @PathParam("trackId") final int trackId) throws APIException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        trackService.removeTrackFromPlaylist(authUser, playlistId, trackId);
        return getAllTracksResponse(playlistId, authUser);
    }


    private Response getAllPlaylistResponse(AuthenticatedUserDTO authUser) throws APIException {
        PlaylistCollectionDTO playlists =playlistService.getAllPlaylist(authUser);
        if(playlists.getPlaylists().isEmpty())
            return Response.status(204).build();
        return Response.ok(playlists).build();
    }

    private Response getAllTracksResponse(int id, AuthenticatedUserDTO authUser) {
        TrackInPlaylistResponse response = new TrackInPlaylistResponse(trackService.getAllTracksFromPlaylist(authUser, id));
        return Response.ok(response).build();
    }

    private AuthenticatedUserDTO createAuthUser(String token) throws APIException {
        return validateToken(token);
    }
}
