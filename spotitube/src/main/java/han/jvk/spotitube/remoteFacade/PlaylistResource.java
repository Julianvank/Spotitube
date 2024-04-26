package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.RestException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.service.IPlaylistService;
import han.jvk.spotitube.service.ITrackService;
import jakarta.inject.Inject;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/playlists")
public class PlaylistResource extends TokenRequiredResource {

    IPlaylistService playlistService;
    ITrackService trackService;

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
    public Response getAllPlaylist(@QueryParam("token") final String token) throws RestException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        return getAllPlaylistResponse(authUser);
    }

    @Path("/:{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") final String token, @PathParam("id") final int id) throws RestException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        playlistService.deletePlaylistById(authUser, id);
        return getAllPlaylistResponse(authUser);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") final String token, PlaylistDTO playlistDTO) throws RestException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        playlistService.addPlaylist(authUser, playlistDTO);
        return getAllPlaylistResponse(authUser);
    }

    @Path("/:{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") final String token, @PathParam("id") final int id, PlaylistDTO playlistDTO) throws RestException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        playlistService.editPlaylist(playlistDTO, id);
        return getAllPlaylistResponse(authUser);
    }



    @Path("/:{id}/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksFromPlaylist(@QueryParam("token") final String token, @PathParam("id") final int id) throws RestException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        return getAllTracksResponse(id, authUser);
    }


    @Path("/:{id}/tracks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") final String token, @PathParam("id") final int playlistId, TrackDTO trackDTO) throws RestException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        try {
            trackService.addTrackToPlaylist(playlistId, trackDTO);
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new RestException("Unable to add Track.", e);
        }
        return getAllTracksResponse(playlistId, authUser);
    }

    @Path("/:{id}/tracks/:{trackId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@QueryParam("token") final String token, @PathParam("id") final int id, @PathParam("trackId") final int trackId) throws RestException {
        AuthenticatedUserDTO authUser = createAuthUser(token);
        trackService.removeTrackFromPlaylist(authUser, id, trackId);
        return getAllTracksResponse(id, authUser);
    }


    private Response getAllPlaylistResponse(AuthenticatedUserDTO authUser) throws RestException {
        try {
            return Response.ok(playlistService.getAllPlaylist(authUser)).build();
        } catch (ServiceException e) {
            throw new RestException(e);
        }
    }

    private Response getAllTracksResponse(int id, AuthenticatedUserDTO authUser) {
        return Response.ok(trackService.getAllTracksFromPlaylist(authUser, id)).build();
    }

    private AuthenticatedUserDTO createAuthUser(String token) throws RestException {
        return validateToken(token);
    }
}
