package com.example.ProiectBackendNew.Controller;

import com.example.ProiectBackendNew.Model.Cuprins;
import com.example.ProiectBackendNew.Repository.CuprinsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class CuprinsController {

    @Autowired
    private CuprinsRepository cuprinsRepository;

    @PostMapping("/Cuprins") // create
    Cuprins createCuprins(@RequestBody Cuprins cuprinsNou) {
        return cuprinsRepository.save(cuprinsNou);
    }

    // toate selecturile
    @GetMapping("/Cuprinsuri")
    List<Cuprins> getAllCuprinsuri() {
        return cuprinsRepository.findAll();
    }

    @GetMapping("/Cuprins/{cuprinsId}")
    Cuprins getCuprinsById(@PathVariable Long cuprinsId) {
        return cuprinsRepository.findById(cuprinsId).orElseThrow(() -> new RuntimeException("Cuprinsul cu acest ID nu exista!"));
    }

    @GetMapping("/CuprinsuriByRestaurant/{restaurantId}")
    List<Cuprins> getCuprinsuriByComanda(@PathVariable Long restaurantId) {
        return cuprinsRepository.findCuprinsByRestaurantId(restaurantId);
    }

    @PutMapping("/Cuprins/{cuprinsId}") // update
    Cuprins updateCuprins(@RequestBody Cuprins cuprinsNou, @PathVariable Long cuprinsId) {
        return cuprinsRepository.findById(cuprinsId).map(cuprins -> {
            cuprins.setCuprinsId(cuprinsNou.getCuprinsId());
            cuprins.setProdusId(cuprinsNou.getProdusId());
            cuprins.setRestaurantId(cuprinsNou.getRestaurantId());
            return cuprinsRepository.save(cuprins);
        }).orElseThrow(() -> new RuntimeException("Cuprinsul cu acest Id nu exista!"));
    }

    @DeleteMapping("/Cuprins/{cuprinsId}") // delete
    String deleteCuprins(@PathVariable Long cuprinsId) {
        if (!cuprinsRepository.existsById(cuprinsId)) {
            throw new RuntimeException("Cuprinsul cu acest Id nu exista!");
        }
        cuprinsRepository.deleteById(cuprinsId);
        return "Cuprinsul cu acest ID a fost sters!";
    }

}
