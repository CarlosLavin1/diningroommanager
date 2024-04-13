package com.example.diningroommanager.jwtCreator.privatekey;

import com.example.diningroommanager.jwtCreator.config.PrivateKeyConfig;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Component
public class PrivateKeyFileService implements PrivateKeyService {

    private final PrivateKeyConfig privateKeyConfig;

    public PrivateKeyFileService(PrivateKeyConfig privateKeyConfig) {
        this.privateKeyConfig = privateKeyConfig;
    }

    public PrivateKey getPrivateKey() throws Exception {

        var privateKeyString = getFileString(privateKeyConfig.getPrivateKeyFilePath());

        if(privateKeyString == null || privateKeyString.isEmpty()){
            return null;
        }

        return getPrivateKey(privateKeyString);
    }

    protected PrivateKey getPrivateKey(String keyString) throws NoSuchAlgorithmException, InvalidKeySpecException {

        var privateKey = keyString
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encodedKey = Base64.decodeBase64(privateKey);
        var keySpec = new PKCS8EncodedKeySpec(encodedKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private String getFileString(String filePath) throws IOException {
        try (var stream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            return convertToString(stream);
        }
    }

    private String convertToString(InputStream stream) throws IOException {

        if(stream == null){
            return null;
        }

        StringBuilder sb = new StringBuilder();

        try (var reader = new BufferedReader(new InputStreamReader(stream))) {
            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
        }

        return sb.toString();
    }
}
