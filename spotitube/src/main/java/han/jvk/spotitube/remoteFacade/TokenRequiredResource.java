package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.exception.APIException;
import han.jvk.spotitube.exception.RestException;
import han.jvk.spotitube.exception.ServiceException;
import han.jvk.spotitube.service.ITokenService;
import jakarta.inject.Inject;

public abstract class TokenRequiredResource {


    private ITokenService tokenService;

    @Inject
    public void setTokenService(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    protected AuthenticatedUserDTO validateToken(String token) throws APIException {
        try {
            return tokenService.getAuthenticatedUserDTOByToken(token);
        } catch (ServiceException e) {
            throw new RestException("The user could not be validated, ", e);
        }
    }

}
