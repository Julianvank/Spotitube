package han.jvk.spotitube.remoteFacade;



import han.jvk.spotitube.service.IHealthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.print.attribute.standard.Media;

@Path("/health")
public class HealthResource {

    private IHealthService healthService;

    @Inject
    public void setHealthService(IHealthService healthService){this.healthService = healthService;}

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response checkHealth(){
        System.out.println("healthy check");
        return Response.ok("Hello World").build();}

    @Path("/inject")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response checkInject(){
        String string = healthService.getHealth();
        return Response.ok(string).build();
    }

}
