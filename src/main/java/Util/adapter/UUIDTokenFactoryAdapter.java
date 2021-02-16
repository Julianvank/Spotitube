package Util.adapter;

import javax.enterprise.inject.Default;
import java.util.UUID;

@Default
public class UUIDTokenFactoryAdapter implements TokenFactoryAdapter {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}