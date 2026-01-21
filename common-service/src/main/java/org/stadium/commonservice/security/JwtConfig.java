package org.stadium.commonservice.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")

public class JwtConfig {
    private String secret;
    private Long expiration;
    private Long refreshExpiration;
    private String issuer;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
