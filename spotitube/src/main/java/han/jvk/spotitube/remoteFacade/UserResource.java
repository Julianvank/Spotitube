package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.dto.exception.RestException;
import han.jvk.spotitube.dto.exception.ServiceException;
import han.jvk.spotitube.service.IUserService;
import jakarta.inject.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class UserResource {

    private IUserService userService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response UserHealth() throws RestException {
        return Response.ok("healthy").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserDTO user) throws RestException {
        try {
            AuthenticatedUserDTO authUser = userService.getUserToken(user);
            return Response.ok(authUser).build();
        } catch (ServiceException e){
            throw new RestException("Could not authenticate user.", e);
        }
    }


    @Inject
    public void setUserService(IUserService userService){this.userService = userService;}

}
