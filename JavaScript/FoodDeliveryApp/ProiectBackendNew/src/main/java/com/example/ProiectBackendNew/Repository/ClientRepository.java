package com.example.ProiectBackendNew.Repository;


import com.example.ProiectBackendNew.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByAdresaId(Long adresaId);
    Optional<Client> findClientByEmail(String email);

    Optional<Client> findClientByParola(String parola);

    Optional<Client> findClientByEmailAndParola(String email,String parola);

}
