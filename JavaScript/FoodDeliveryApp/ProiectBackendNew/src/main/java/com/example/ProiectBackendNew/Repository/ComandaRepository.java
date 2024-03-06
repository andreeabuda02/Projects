package com.example.ProiectBackendNew.Repository;

import com.example.ProiectBackendNew.Model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComandaRepository extends JpaRepository <Comanda, Long> {

    List<Comanda> findComandaByClientId(Long clientId);
    Optional<Comanda> findComandaByClientIdAndComandaId(Long clientId, Long comandaId);

}
