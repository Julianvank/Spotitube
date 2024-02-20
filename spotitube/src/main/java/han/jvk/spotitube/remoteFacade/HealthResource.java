package han.jvk.spotitube.remoteFacade;



import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/health")
public class HealthResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response checkHealth(){return Response.ok("<h1>Hello World</h1>").build();}
}
