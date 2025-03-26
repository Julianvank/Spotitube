package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.UserDTO;
import han.jvk.spotitube.exception.APIException;
import han.jvk.spotitube.remoteFacade.responses.LogInUserResponse;
import han.jvk.spotitube.service.IUserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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

        if(authUser == null){
            return Response.status(401).build();
        }

        LogInUserResponse response = new LogInUserResponse(authUser.getToken(), authUser.getUsername());
        return Response.ok(response).build();

    }

    @Inject
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
