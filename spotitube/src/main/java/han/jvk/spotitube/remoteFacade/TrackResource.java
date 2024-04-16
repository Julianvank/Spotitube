package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.exception.RestException;
import han.jvk.spotitube.service.ITrackService;
import jakarta.inject.Inject;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tracks")
public class TrackResource extends TokenRequiredResource {

    ITrackService trackService;

    @Inject
    public void setTrackService(ITrackService trackService) {
        this.trackService = trackService;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableTracks(@QueryParam("token") final String token, @QueryParam("forPlaylist") final int id) throws RestException {
        AuthenticatedUserDTO authUser = validateToken(token);
        return Response.ok(trackService.getAvailableTracks(authUser, id)).build();
    }

}

