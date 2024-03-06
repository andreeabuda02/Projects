package com.example.ProiectBackendNew.Repository;

import com.example.ProiectBackendNew.Model.Cuprins;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuprinsRepository extends JpaRepository<Cuprins, Long>{

    List<Cuprins> findCuprinsByRestaurantId(Long restaurantId);
}
