package Util.factory;

import Util.adapter.TokenFactoryAdapter;
import Util.adapter.UUIDTokenFactoryAdapter;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Default
public class TokenFactory implements ITokenFactory {
    private TokenFactoryAdapter tokenFactoryAdapter = new UUIDTokenFactoryAdapter();

    @Inject
    public void setTokenFactoryAdapter(TokenFactoryAdapter tokenFactoryAdapter) {
        this.tokenFactoryAdapter = tokenFactoryAdapter;
    }

    @Override
    public String generateToken() {
        return tokenFactoryAdapter.generate();
    }
}
