package org.stadium.userservice.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.stadium.userservice.model.User;

@Repository
public interface UserRepository {

    Optional<User> findbyEmail(String email);
    boolean existsByEmail(String email);

    
} 
