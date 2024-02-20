package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.RestException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.service.IUserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
public class UserResource {

    private IUserService userService;
    @Inject
    public void setUserService(IUserService userService){this.userService = userService;}

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(UserDTO user) throws RestException {
        try {
            AuthenticatedUserDTO authUser = userService.getUserToken(user);
            return Response.ok("healthy").build();
//            return Response.ok(authUser).build();
        } catch (ServiceException e){
            e.printStackTrace();
            throw new RestException("Could not authenticate user.", e);
        }
    }


}
