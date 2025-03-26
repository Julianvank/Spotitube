package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.exception.APIException;
import han.jvk.spotitube.remoteFacade.responses.TrackInPlaylistResponse;
import han.jvk.spotitube.service.ITrackService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
    public Response getAvailableTracks(@QueryParam("token") final String token, @QueryParam("forPlaylist") final int id) throws APIException {
        validateToken(token);
        TrackInPlaylistResponse response = new TrackInPlaylistResponse(trackService.getAvailableTracks(id));
        //TODO fixen
        return Response.ok(response).build();
    }

}

