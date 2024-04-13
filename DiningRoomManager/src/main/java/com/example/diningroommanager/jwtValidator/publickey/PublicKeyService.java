package com.example.diningroommanager.jwtValidator.publickey;

import java.security.PublicKey;

public interface PublicKeyService {
    PublicKey getPublicKey() throws Exception;

    String getPublicKeyString() throws Exception;
}
