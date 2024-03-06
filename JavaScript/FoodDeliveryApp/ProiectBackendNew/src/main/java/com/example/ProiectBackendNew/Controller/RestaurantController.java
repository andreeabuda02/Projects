package com.example.ProiectBackendNew.Controller;


import com.example.ProiectBackendNew.Model.Restaurant;
import com.example.ProiectBackendNew.Repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping("/Restaurant") // create
    Restaurant createRestaurant(@RequestBody Restaurant restaurantNou) {
        return restaurantRepository.save(restaurantNou);
    }

    // toate selecturile
    @GetMapping("/Restaurante")
    List<Restaurant> getAllRestaurante() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/Restaurant/{restaurantId}")
    Restaurant getRestaurantById(@PathVariable Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurantul cu acest ID nu exista!"));
    }

    @GetMapping("/RestaurantByAdresa/{adresaId}")
    Restaurant getRestaurantByAdresa(@PathVariable Long adresaId) {
        return restaurantRepository.findRestaurantByAdresaId(adresaId)
                .orElseThrow(() -> new RuntimeException("Restaurantul cu aceasta adresa nu exista!"));
    }

    @PutMapping("/Restaurant/{restaurantId}") // update
    Restaurant updateRestaurant(@RequestBody Restaurant restaurantNou, @PathVariable Long restaurantId) {
        return restaurantRepository.findById(restaurantId).map(restaurant -> {
            restaurant.setRestaurantId(restaurantNou.getRestaurantId());
            restaurant.setNume(restaurantNou.getNume());
            restaurant.setOraDeschidere(restaurantNou.getOraDeschidere());
            restaurant.setOraInchidere(restaurantNou.getOraInchidere());
            restaurant.setAdresaId(restaurantNou.getAdresaId());
            return restaurantRepository.save(restaurant);
        }).orElseThrow(() -> new RuntimeException("Restaurantul cu acest Id nu exista!"));
    }

    @DeleteMapping("/Restaurant/{restaurantId}") // delete
    String deleteRestaurant(@PathVariable Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurantul cu acest Id nu exista!");
        }
        restaurantRepository.deleteById(restaurantId);
        return "Restaurantul cu acest ID a fost sters!";
    }

}
