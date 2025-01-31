package org.stadium.stadium_management.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.stadium.stadium_management.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

