package com.example.PsProiectAgentieDeTurismSERVER.Repository;

import com.example.PsProiectAgentieDeTurismSERVER.Model.RezervarePachet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface RezervarePachetRepository extends JpaRepository<RezervarePachet, Integer> {
    Optional<RezervarePachet> findRezervarePachetByIdPachetAndIdClient(Integer idClient, Integer idPachet);
    Optional<List<RezervarePachet>> findRezervarePachetsByIdClient(Integer idClient);
    Optional<List<RezervarePachet>> findRezervarePachetsByIdPachet(Integer idPachet);
    Optional<List<RezervarePachet>> findRezervarePachetByDataRezervare(Date data);
}
