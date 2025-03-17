package com.example.PsProiectAgentieDeTurismSERVER.Repository;

import com.example.PsProiectAgentieDeTurismSERVER.Model.PachetTuristic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface PachetTuristicRepository extends JpaRepository<PachetTuristic, Integer> {
    Optional<List<PachetTuristic>> findPachetTuristicsByDestinatieAndPerioadaOrderByDestinatieAscPerioadaAsc(String destinatie, Date perioada);
    Optional<List<PachetTuristic>> findPachetTuristicsByDestinatieOrderByDestinatieAscPerioadaAsc(String destinatie);
    Optional<List<PachetTuristic>> findPachetTuristicsByPerioadaOrderByDestinatieAscPerioadaAsc(Date perioada);
    Optional<List<PachetTuristic>> findPachetTuristicsByDestinatieAndPerioadaAndPretIsLessThanEqualOrderByDestinatieAscPerioadaAsc(String destinatie, Date perioada, Double pret);
    Optional<List<PachetTuristic>> findPachetTuristicsByPretIsLessThanEqualOrderByDestinatieAscPerioadaAsc(Double pret);
}
