package com.example.ProiectBackendNew.Repository;

import com.example.ProiectBackendNew.Model.Produs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdusRepository extends JpaRepository<Produs, Long> {
}
