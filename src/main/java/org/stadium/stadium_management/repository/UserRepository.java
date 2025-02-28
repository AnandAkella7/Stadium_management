package org.stadium.stadium_management.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.stadium.stadium_management.model.User;

import java.util.Optional;

import java.util.HashMap;
import java.util.Map;
import java.util.List;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // HashMap cache for quick lookups 
    Map<Long, User> userCache = new HashMap<>();

    Optional<User> findByEmail(String email);

    Optional<User> findCacheUser(Long id);
    
    





}

