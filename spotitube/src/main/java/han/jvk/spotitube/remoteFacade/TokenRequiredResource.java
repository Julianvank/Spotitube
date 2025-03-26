package han.jvk.spotitube.remoteFacade;

import han.jvk.spotitube.dto.AuthenticatedUserDTO;
import han.jvk.spotitube.service.ITokenService;
import jakarta.inject.Inject;

public abstract class TokenRequiredResource {

    private ITokenService tokenService;

    @Inject
    public void setTokenService(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    protected AuthenticatedUserDTO validateToken(String token) {
        return tokenService.getAuthenticatedUserDTOByToken(token);
    }
}
