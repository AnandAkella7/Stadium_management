package org.stadium.commonservice.security;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.stadium.commonservice.dto.UserDetailsDto;
import org.stadium.commonservice.feign.UserFeignClient;



import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserFeignClient userFeignClient;
 
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, java.io.IOException {// Fix IOException import
            try {
                String jwt = parseJwt(request);
                System.out.println("JWT Token parsed: " + (jwt != null ? "YES" : "NO"));
                
                if (jwt != null) {
                    boolean isValid = jwtUtils.validateJwtToken(jwt);
                    System.out.println("JWT Token valid: " + (isValid ? "YES" : "NO"));
                    
                    if (isValid) {
                        String email = jwtUtils.getEmailFromJwtToken(jwt);
                        System.out.println("Email from token: " + email);
                        
                        try {
                            UserDetailsDto userDetails = circuitBreakerFactory.create("user-service")
                                .run(() -> userFeignClient.getUserByEmail(email),
                                    throwable -> { 
                                        System.out.println("Circuit breaker error: " + throwable.getMessage());
                                        throw new ResponseStatusException(
                                            HttpStatus.SERVICE_UNAVAILABLE,
                                            "User service unavailable"
                                        );
                                    });
                            
                            System.out.println("UserDetailsDto retrieved successfully");
                            
                            // Create proper authentication object
                            UsernamePasswordAuthenticationToken authentication = 
                                new UsernamePasswordAuthenticationToken(
                                    userDetails.toUserDetails(),
                                    jwt,
                                    userDetails.toUserDetails().getAuthorities()
                                );
                            authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                            );
                            
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            System.out.println("Authentication set in SecurityContextHolder");
                        } catch (Exception e) {
                            System.out.println("Error retrieving user details: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
                
                // Log current authentication state before proceeding
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                System.out.println("Current authentication: " + (auth != null ? auth.getName() + ", Authenticated: " + auth.isAuthenticated() : "NULL"));
                
            } catch (ResponseStatusException ex) {
                System.out.println("ResponseStatusException: " + ex.getMessage());
                response.sendError(ex.getStatusCode().value(), ex.getReason());
                return;
            } catch (Exception ex) {
                System.out.println("Unexpected exception: " + ex.getMessage());
                ex.printStackTrace();
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return;
            }
            
            filterChain.doFilter(request, response);
            }
                                    

    // Jwt parsing
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}