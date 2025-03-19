package org.stadium.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.stadium.userservice.entity.User;
import org.stadium.userservice.exception.ResourceNotFoundException;
import org.stadium.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public User getUserProfileByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
}