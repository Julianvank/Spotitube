package han.jvk.spotitube.remoteFacade;



import han.jvk.spotitube.dto.HealthDTO;
import han.jvk.spotitube.dto.TrackDTO;
import han.jvk.spotitube.service.IHealthService;
import han.jvk.spotitube.util.factory.DBConnection.IDBConnectionFactory;
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

    private IDBConnectionFactory connector;

    @Inject
    public void setConnector(IDBConnectionFactory connector){
        this.connector = connector;
    }

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

    @Path("/test")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkArray(HealthDTO healthDTO){
        for (TrackDTO string : healthDTO.getTracks()) {
            System.out.println("The string to return is: " + string.toString());
        }
        return Response.ok().build();
    }

    @Path("/change")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response changeDatabase(String database){
        connector.setProperties(database);
        return Response.ok(database).build();
    }
}
