package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.exception.RestException;
import han.jvk.spotitube.service.ITrackService;
import jakarta.inject.Inject;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class TrackResource extends TokenRequiredResource{

    ITrackService trackService;

    @Inject
    public void setTrackService(ITrackService trackService){
        this.trackService = trackService;
    }

    @Path("/playlists/:{id}/tracks")
    @GET
    public Response getAllTracksFromPlaylist(@QueryParam("token") final String token, @PathParam("id") final int id) throws RestException {
        AuthenticatedUserDTO authUser = validateToken(token);
        return Response.ok(trackService.getAllTracksFromPlaylist(authUser, id)).build();
    }

    @Path("/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableTracks(@QueryParam("token") final String token, @QueryParam("forPlaylist") final int id) throws RestException {
        AuthenticatedUserDTO authUser = validateToken(token);
        trackService.getAvailableTracks(authUser, id);
        return Response.ok(trackService.getAllTracksFromPlaylist(authUser, id)).build();
    }

    @Path("/playlists/:{id}/tracks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") final String token, @PathParam("id") final int id, TrackDTO trackDTO) throws RestException {
        AuthenticatedUserDTO authUser = validateToken(token);
        trackService.addTrackToPlaylist(authUser, id, trackDTO);
        return Response.ok(trackService.getAllTracksFromPlaylist(authUser, id)).build();
    }

    @Path("/playlists/:{id}/tracks/:{trackId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@QueryParam("token") final String token, @PathParam("id") final int id, @PathParam("trackId") final int trackId) throws RestException {
        AuthenticatedUserDTO authUser = validateToken(token);
        trackService.removeTrackFromPlaylist(authUser, id, trackId);
        return Response.ok(trackService.getAllTracksFromPlaylist(authUser, id)).build();
    }
}

