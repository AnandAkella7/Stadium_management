package org.stadium.commonservice.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put("authorities", userDetails.getAuthorities());
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuer(jwtConfig.getIssuer())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+ jwtConfig.getExpiration()))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

}
