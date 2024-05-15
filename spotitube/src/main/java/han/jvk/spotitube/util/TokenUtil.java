package han.jvk.spotitube.util;

import han.jvk.spotitube.util.adapter.ITokenFactoryAdapter;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class TokenUtil implements ITokenFactoryAdapter {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
