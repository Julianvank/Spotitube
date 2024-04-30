package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.APIException;
import han.jvk.spotitube.service.IUserService;
import jakarta.inject.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class UserResource {


    private IUserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserDTO user) throws APIException {
        AuthenticatedUserDTO authUser = userService.getUserToken(user);
        return Response.ok(authUser).build();
    }


    @Inject
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

}
