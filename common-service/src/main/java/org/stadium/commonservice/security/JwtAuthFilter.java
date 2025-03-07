package org.stadium.commonservice.security;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.stadium.commonservice.dto.UserDetailsDto;
import org.stadium.commonservice.feign.UserFeignClient;



import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
                                   FilterChain filterChain) throws ServletException, java.io.IOException { // Fix IOException import
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) { // Changed method name
                String email = jwtUtils.getEmailFromJwtToken(jwt); // Changed method name
                
                UserDetailsDto userDetails = circuitBreakerFactory.create("user-service")
                    .run(() -> userFeignClient.getUserByEmail(email),
                        throwable -> { 
                            throw new ResponseStatusException( // Use Spring exception
                                HttpStatus.SERVICE_UNAVAILABLE,
                                "User service unavailable"
                            );
                        });

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
            }
        } catch (ResponseStatusException ex) {
            response.sendError(ex.getStatusCode().value(), ex.getReason());
        } catch (Exception ex) {
            logger.error("Authentication error", ex);
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        filterChain.doFilter(request, response);
    }

    // Add this method
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}