package han.jvk.spotitube.util;

import han.jvk.spotitube.util.adapter.TokenFactoryAdapter;

import java.util.UUID;

public class TokenUtil implements TokenFactoryAdapter {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
