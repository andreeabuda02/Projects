package com.example.ProiectBackendNew.Controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.ProiectBackendNew.Model.Produs;
import com.example.ProiectBackendNew.Repository.ProdusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ProdusController {

    @Autowired
    private ProdusRepository produsRepository;

    @PostMapping("/Produs") // create
    Produs createProdus(@RequestBody Produs produsNou) {
        return produsRepository.save(produsNou);
    }

    // toate selecturile
    @GetMapping("/Produse")
    List<Produs> getAllProduse() {
        return produsRepository.findAll();
    }

    @GetMapping("/Produs/{produsId}")
    Produs getProdusById(@PathVariable Long produsId) {
        return produsRepository.findById(produsId).orElseThrow(() -> new RuntimeException("Produsul cu acest ID nu exista!"));
    }

    @PutMapping("/Produs/{produsId}") // update
    Produs updateProdus(@RequestBody Produs produsNou, @PathVariable Long produsId) {
        return produsRepository.findById(produsId).map(produs -> {
            produs.setProdusId(produsNou.getProdusId());
            produs.setNume(produsNou.getNume());
            produs.setPret(produsNou.getPret());
            return produsRepository.save(produs);
        }).orElseThrow(() -> new RuntimeException("Produsul cu acest Id nu exista!"));
    }

    @DeleteMapping("/Produs/{produsId}") // delete
    String deleteProdus(@PathVariable Long produsId) {
        if (!produsRepository.existsById(produsId)) {
            throw new RuntimeException("Produsul cu acest Id nu exista!");
        }
        produsRepository.deleteById(produsId);
        return "Produsul cu acest ID a fost sters!";
    }
}
