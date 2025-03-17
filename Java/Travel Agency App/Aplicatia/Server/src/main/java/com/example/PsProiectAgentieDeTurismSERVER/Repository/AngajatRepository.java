package com.example.PsProiectAgentieDeTurismSERVER.Repository;

import com.example.PsProiectAgentieDeTurismSERVER.Model.Angajat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AngajatRepository extends JpaRepository<Angajat, Integer> {
    Optional<Angajat> findByEmailAndParola(String email, String parola);

}
