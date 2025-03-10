package org.stadium.userservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.stadium.userservice.entity.RefreshToken;
import org.stadium.userservice.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    
    Optional<RefreshToken> findByToken(String token);
    
    void deleteByUser(User user);
    
    // Optional: Find by user email
    Optional<RefreshToken> findByUserEmail(String email);
}