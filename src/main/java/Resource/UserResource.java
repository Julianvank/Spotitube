package Resource;

import DTO.AuthenticatedUserDTO;
import DTO.UserDTO;
import Exception.RestException;
import Service.IUserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("")
public class UserResource {

    private IUserService userService;

    @Inject
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Authenticated de user.
     * @param userDTO de gegevens om mee in te loggen.
     * @return Een Response met de authenticated user
     * @throws RestException
     */
    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(UserDTO userDTO) throws RestException {
            try {
                AuthenticatedUserDTO auth = userService.getAuthenticatedUserDTO(userDTO);
                return Response.ok(auth).build();
            }catch (Exception e){
                throw new RestException("Could not authenticate. " + e);
            }
    }

}
