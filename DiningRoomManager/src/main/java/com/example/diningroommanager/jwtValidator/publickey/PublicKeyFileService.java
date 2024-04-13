package com.example.diningroommanager.jwtValidator.publickey;


import com.example.diningroommanager.jwtValidator.config.PublicKeyConfig;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Component
public class PublicKeyFileService implements PublicKeyService {

    private final PublicKeyConfig publicKeyConfig;

    public PublicKeyFileService(PublicKeyConfig publicKeyConfig) {
        this.publicKeyConfig = publicKeyConfig;
    }

    public PublicKey getPublicKey() throws Exception {

        var publicKeyString = getPublicKeyString();

        if(publicKeyString == null){
            return  null;
        }

        return getRsaPublicKey(publicKeyString);
    }

    public String getPublicKeyString() throws Exception {
        return getFileString(publicKeyConfig.getPublicKeyFilePath());
    }


    protected PublicKey getRsaPublicKey(String keyFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        var keySpec = getRsaPublicKeySpec(keyFile);
        return getRsaPublicKey(keySpec);
    }

    protected EncodedKeySpec getRsaPublicKeySpec(String keyFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        String publicKey = keyFile
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.decodeBase64(publicKey);

        return getEncodedKeySpec(encoded);
    }

    protected EncodedKeySpec getEncodedKeySpec(byte[] encodedKey) {
        try {
            return new X509EncodedKeySpec(encodedKey);
        } catch (Exception ex) {
            throw ex;
        }
    }

    protected PublicKey getRsaPublicKey(EncodedKeySpec keySpec) throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
        return publicKeyFactory.generatePublic(keySpec);
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