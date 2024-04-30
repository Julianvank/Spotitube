package han.jvk.spotitube.remoteFacade;



import han.jvk.spotitube.util.factory.DBConnection.IDBConnectionFactory;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/health")
public class HealthResource {

    private static final Logger log = Logger.getLogger(HealthResource.class.getName());

    private IDBConnectionFactory connector;

    @Inject
    public void setConnector(IDBConnectionFactory connector){
        this.connector = connector;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response checkHealth(){
        log.info("Health check succefully reached!");
        return Response.ok("Hello World").build();}



    @Path("/change")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response changeDatabase(String database){
        connector.setProperties(database);
        return Response.ok(database).build();
    }
}
