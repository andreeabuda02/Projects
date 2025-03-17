package com.example.PsProiectAgentieDeTurismSERVER.Repository;

import com.example.PsProiectAgentieDeTurismSERVER.Model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    Optional<Administrator> findByEmailAndParola(String email, String parola);

}
