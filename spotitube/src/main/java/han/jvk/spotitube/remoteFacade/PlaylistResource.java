package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.PlaylistDTO;
import han.jvk.spotitube.exception.RestException;
import han.jvk.spotitube.service.IPlaylistService;
import jakarta.inject.Inject;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class PlaylistResource extends TokenRequiredResource{

    IPlaylistService playlistService;

    @Inject
    public void setPlaylistService(IPlaylistService playlistService){
        this.playlistService = playlistService;
    }

    @Path("/playlists")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylist(@QueryParam("token") final String token) throws RestException {
        AuthenticatedUserDTO authUser = validateToken(token);
        return Response.ok(playlistService.getAllPlaylist(authUser)).build();
    }

    @Path("/playlists/:{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") final String token, @PathParam("id") final int id) throws RestException {
        AuthenticatedUserDTO authUser = validateToken(token);
        playlistService.deletePlaylistById(authUser, id);
        return Response.ok(playlistService.getAllPlaylist(authUser)).build();
    }

    @Path("/playlists")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") final String token, PlaylistDTO playlistDTO) throws RestException {
        AuthenticatedUserDTO authUser = validateToken(token);
        playlistService.addPlaylist(authUser, playlistDTO);
        return Response.ok(playlistService.getAllPlaylist(authUser)).build();
    }

    @Path("/playlists/:{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") final String token, @PathParam("id") final int id, PlaylistDTO playlistDTO) throws RestException {
        AuthenticatedUserDTO authUser = validateToken(token);
        playlistService.editPlaylist(authUser, playlistDTO);
        return Response.ok(playlistService.getAllPlaylist(authUser)).build();
    }
}
