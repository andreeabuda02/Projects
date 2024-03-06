package com.example.ProiectBackendNew.Repository;

import com.example.ProiectBackendNew.Model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findRestaurantByAdresaId(Long adresaId);


}
