package com.example.ProiectBackendNew.Repository;

import com.example.ProiectBackendNew.Model.Adresa;
import com.example.ProiectBackendNew.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdresaRepository extends JpaRepository<Adresa, Long> {
    Optional<Adresa> findAdresaByOrasulAndStrada(String orasul, String strada);
    Optional<Adresa> findAdresaByTaraAndJudetul(String tara, String judetul);
    Optional<Adresa> findAdresaByTaraAndJudetulAndOrasulAndStradaAndNumarul(String tara, String judetul, String orasul, String strada, String numarul);
}
