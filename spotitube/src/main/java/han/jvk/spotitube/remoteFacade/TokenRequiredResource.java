package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.dto.exception.RestException;
import han.jvk.spotitube.dto.exception.ServiceException;
import han.jvk.spotitube.service.ITokenService;
import jakarta.inject.Inject;


public abstract class TokenRequiredResource {

    private ITokenService tokenService;

    @Inject
    public void setTokenService(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    protected AuthenticatedUserDTO validateToken(String token) throws RestException {
        try {
            return tokenService.getAuthenticatedUserDTOByToken(token);
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new RestException("This resource requires a token.", e);
        }
    }

}
