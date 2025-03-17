package com.example.Microservice_User_A1.repositories;

import com.example.Microservice_User_A1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUsersByEmail(String email);
}
