package org.stadium.userservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stadium.userservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,UUID>{

    Optional<User> findbyEmail(String email);
    boolean existsByEmail(String email);

    
} 
