package com.salinas.test.usermanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salinas.test.usermanager.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
