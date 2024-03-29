package han.jvk.spotitube.util.factory;

import han.jvk.spotitube.util.TokenUtil;
import han.jvk.spotitube.util.adapter.TokenFactoryAdapter;
import jakarta.inject.Inject;

public class TokenFactory implements ITokenFactory{

    private TokenFactoryAdapter tokenFactoryAdapter = new TokenUtil();

    @Inject
    public void setTokenFactoryAdapter(TokenFactoryAdapter tokenFactoryAdapter) {
        this.tokenFactoryAdapter = tokenFactoryAdapter;
    }

    @Override
    public String generateToken() {
        return tokenFactoryAdapter.generate();
    }
}
