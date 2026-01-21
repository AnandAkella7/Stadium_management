package org.stadium.userservice.controller;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.stadium.commonservice.authorization.UserRole;
import org.stadium.commonservice.security.JwtConfig;
import org.stadium.commonservice.security.JwtUtils;
import org.stadium.userservice.dto.LoginRequest;
import org.stadium.userservice.dto.RefreshTokenRequest;
import org.stadium.userservice.dto.RegisterRequest;
import org.stadium.userservice.dto.TokenResponseDto;
import org.stadium.userservice.entity.RefreshToken;
import org.stadium.userservice.entity.User;
import org.stadium.userservice.repository.UserRepository;
import org.stadium.userservice.service.CustomUserDetailsService;
import org.stadium.userservice.service.RefreshTokenService;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final JwtConfig jwtConfig;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Check if email already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }        

        // Create new user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUserRole(UserRole.USER); // Default role

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        
        try{
        System.out.println("Login attempt for: " + loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), 
                loginRequest.getPassword()
            )
        );
        System.out.println("Authentication successful");
            

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);

        System.out.println("JWT token generated");

        // Generate RefreshToken
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        System.out.println("Refresh token generated");
        return ResponseEntity.ok(
            new TokenResponseDto(
                jwt,
                refreshToken.getToken(),
                jwtConfig.getExpiration() // Or calculate expiresIn
            )
        );
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Login error: " + e.getMessage());
            return ResponseEntity.status(403).body(Map.of("error", "Invalid login" + e.getMessage()));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
                String accessToken = jwtUtils.generateToken(userDetails);
                return ResponseEntity.<Object>ok(
                    new TokenResponseDto(
                        accessToken,
                        request.getRefreshToken(),
                        jwtConfig.getExpiration()
                    )
                );
            })
            .orElseGet(() -> 
                ResponseEntity.badRequest().body(
                    Map.of("error", "Invalid refresh token")
                )
            );
    }
}
