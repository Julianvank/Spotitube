package han.jvk.spotitube.util.factory.Token;

import han.jvk.spotitube.util.TokenUtil;
import han.jvk.spotitube.util.adapter.ITokenFactoryAdapter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TokenFactory implements ITokenFactory{

    private ITokenFactoryAdapter tokenFactoryAdapter = new TokenUtil();

    @Inject
    public void setTokenFactoryAdapter(ITokenFactoryAdapter tokenFactoryAdapter) {
        this.tokenFactoryAdapter = tokenFactoryAdapter;
    }

    @Override
    public String generateToken() {
        return tokenFactoryAdapter.generate();
    }
}
