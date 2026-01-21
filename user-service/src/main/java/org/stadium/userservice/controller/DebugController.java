// Create a new file: src/main/java/org/stadium/userservice/controller/DebugController.java
package org.stadium.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/debug")
@Slf4j
public class DebugController {

    @GetMapping("/auth")
    public ResponseEntity<Map<String, Object>> debugAuth(
            Authentication authentication, 
            HttpServletRequest request) {
        
        log.info("Debug endpoint called");
        Map<String, Object> debugInfo = new HashMap<>();
        
        // Authentication details
        if (authentication != null) {
            debugInfo.put("isAuthenticated", authentication.isAuthenticated());
            debugInfo.put("principalType", authentication.getPrincipal().getClass().getName());
            debugInfo.put("principalDetails", authentication.getPrincipal().toString());
            debugInfo.put("credentials", authentication.getCredentials() != null ? "PRESENT" : "NULL");
            debugInfo.put("name", authentication.getName());
            debugInfo.put("authorities", authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
        } else {
            debugInfo.put("authentication", "NULL");
        }
        
        // Request headers (to check Authorization header)
        Map<String, String> headers = new HashMap<>();
        Collections.list(request.getHeaderNames()).forEach(
            headerName -> headers.put(headerName, request.getHeader(headerName))
        );
        // Redact sensitive information for safety
        if (headers.containsKey("authorization")) {
            String authHeader = headers.get("authorization");
            headers.put("authorization", 
                authHeader.startsWith("Bearer ") ? "Bearer [REDACTED]" : "[REDACTED]");
        }
        debugInfo.put("headers", headers);
        
        // Security context info
        Authentication securityAuth = SecurityContextHolder.getContext().getAuthentication();
        if (securityAuth != null) {
            debugInfo.put("securityContextAuthentication", "PRESENT");
            debugInfo.put("securityContextAuthenticated", securityAuth.isAuthenticated());
            debugInfo.put("securityContextAuthorities", securityAuth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
        } else {
            debugInfo.put("securityContextAuthentication", "NULL");
        }
        
        return ResponseEntity.ok(debugInfo);
    }
}
