package com.example.diningroommanager.jwtCreator.jwt;

import com.example.diningroommanager.authDTO.dtos.UserRequestDTO;
import com.example.diningroommanager.jwtCreator.config.JWTConfig;
import com.example.diningroommanager.jwtCreator.privatekey.PrivateKeyService;
import io.jsonwebtoken.Jwts;

import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.diningroommanager.authDTO.types.JWTClaimNames.*;


@Component
public class JWTCreator implements JWTRetriever {

    private final PrivateKeyService privateKeyService;

    private final JWTConfig jwtConfig;


    public JWTCreator(PrivateKeyService privateKeyService, JWTConfig jwtConfig) {
        this.privateKeyService = privateKeyService;
        this.jwtConfig = jwtConfig;
    }

    public String generateJWTToken(UserRequestDTO user) throws Exception {

        var privateKey = privateKeyService.getPrivateKey();

        return generateJWTToken(user, privateKey, jwtConfig.getTimeout());
    }

    protected String generateJWTToken(UserRequestDTO user, PrivateKey privateKey, long timeout) {
        String token = null;
        try {
            var nowMillis = System.currentTimeMillis();
            var expirationMillis = System.currentTimeMillis() + 1000 * timeout;
            var claims = getClaims(user);

            var nowDate = new Date(nowMillis);
            var expirationDate = new Date(expirationMillis);

            token = Jwts.builder()
                    .subject(user.getUserName())
                    .claims(claims) // Set the claims from the map
                    .issuedAt(nowDate) // Set the issued date
                    .expiration(expirationDate)
                    .signWith(privateKey) // Sign the token with the private key and algorithm
                    .compact(); // Build the token

        } catch (Throwable e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return token;
    }

    private Map<String, Object> getClaims(UserRequestDTO user){
        var claims = new HashMap<String, Object>();

        claims.put(USER_CLAIM, user.getUserName());
        claims.put(EMAIL_CLAIM, user.getEmail());
        claims.put(ROLES_CLAIM, user.getRoles());

        return claims;
    }
}
