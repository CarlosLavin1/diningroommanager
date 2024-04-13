package com.example.diningroommanager.jwtValidator.jwt;

import com.example.diningroommanager.authDTO.dtos.UserTokenDTO;
import com.example.diningroommanager.authDTO.enums.DefaultRoles;
import com.example.diningroommanager.jwtValidator.publickey.PublicKeyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.diningroommanager.jwtValidator.jwt.ClaimsConverter.getRoles;


@Component
public class JWTValidationService implements JWTValidator {

    private final PublicKeyService publicKeyService;

    public JWTValidationService(PublicKeyService publicKeyService) {
        this.publicKeyService = publicKeyService;
    }


    @Override
    public UserTokenDTO getUser(String token) throws Exception {
        var claims = this.getClaims(token);
        List<String> roles = getRoles(claims);

        return new UserTokenDTO(token, claims.getSubject(), claims, roles);
    }

    public Map<String, Object> getTokenClaims(String token) throws Exception {
        return getClaims(token);
    }

    @Override
    public boolean isValid(String token) throws Exception {
        return getTokenClaims(token) != null;
    }

    @Override
    public boolean isValid(String token, String ... roles) throws Exception {
        return isValid(token, Arrays.asList(roles));
    }

    @Override
    public boolean isValid(String token, DefaultRoles... defaultRoles) throws Exception {
        return isValid(token, DefaultRoles.getNames(defaultRoles));
    }

    @Override
    public boolean isValid(String token, List<String> roles) throws Exception {
        var claims = getTokenClaims(token);
        return isValid(claims, roles);
    }

    private Claims getClaims(String token) throws Exception {

        if (token.isBlank()) {
            return null;
        }

        var publicKey = publicKeyService.getPublicKey();

        return getClaims(publicKey, token);
    }

    private Claims getClaims(PublicKey publicKey, String token) {

        // Parse the token and verify its signature
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private boolean isValid(Map<String, Object> claims, List<String> roles) {
        List<String> claimRoles = getRoles(claims);

        if(claimRoles == null){
            return false;
        }

        return isValid(roles, claimRoles);
    }

    private boolean isValid(List<String> requiredRoles, List<String> currentRoles) {

        for (var role : requiredRoles) {
            if (!currentRoles.contains(role)){
                return false;
            }
        }

        return true;
    }
}
