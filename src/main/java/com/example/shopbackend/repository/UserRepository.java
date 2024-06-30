package com.example.shopbackend.repository;

import com.example.shopbackend.enums.UserRole;
import com.example.shopbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByEmail(String email);

    User findByRole(UserRole userRole);
}
