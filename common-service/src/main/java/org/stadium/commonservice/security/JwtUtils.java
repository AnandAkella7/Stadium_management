package org.stadium.commonservice.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put("authorities", userDetails.getAuthorities());
        return Jwts.builder()
            .claims(claims)
            .subject(userDetails.getUsername())
            .issuer(jwtConfig.getIssuer())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+ jwtConfig.getExpiration()))
            .signWith(secretKey)
            .compact();
    }
    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(authToken);
            return true;
        } catch (SecurityException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;

    }
    public String getEmailFromJwtToken(String token)
    {
        return Jwts.parser()
        .verifyWith(jwtConfig.secretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();

    }

}
