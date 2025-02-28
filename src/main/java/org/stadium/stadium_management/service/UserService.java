package org.stadium.stadium_management.service;

import java.util.List;
import java.util.Optional;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.stadium.stadium_management.model.User;
import org.stadium.stadium_management.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        {
            Optional<User> CachedUser = userRepository.findCacheUser(id);
            if (CachedUser.isPresent()) {
                return CachedUser;
            }
            else {
                userRepository.findById(id);
            }
        }
    }

    public synchronized User;



}
