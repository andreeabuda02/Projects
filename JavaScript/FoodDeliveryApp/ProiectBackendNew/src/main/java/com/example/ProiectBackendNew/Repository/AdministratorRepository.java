package com.example.ProiectBackendNew.Repository;


import com.example.ProiectBackendNew.Model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    Optional<Administrator> findAdministratorByUsername(String username);
    Optional<Administrator> findAdministratorByPassword(String password);
    Optional<Administrator> findAdministratorByUsernameAndPassword(String username,String password);
}
