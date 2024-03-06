package com.example.ProiectBackendNew.Controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.ProiectBackendNew.Model.Comanda;
import com.example.ProiectBackendNew.Repository.ComandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:3000")
public class ComandaController {

    @Autowired
    private ComandaRepository comandaRepository;

    @PostMapping("/Comanda") // create
    Comanda createComanda(@RequestBody Comanda comandaNoua) {
        return comandaRepository.save(comandaNoua);
    }

    // toate selecturile
    @GetMapping("/Comenzi")
    List<Comanda> getAllComenzi() {
        return comandaRepository.findAll();
    }

    @GetMapping("/Comanda/{comandaId}")
    Comanda getComandaById(@PathVariable Long comandaId) {
        return comandaRepository.findById(comandaId).orElseThrow(() -> new RuntimeException("Comanda cu acest ID nu exista!"));
    }

    @GetMapping("/ComenziByClient/{clientId}")
    List<Comanda> getComenziByClient(@PathVariable Long clientId) {
        return comandaRepository.findComandaByClientId(clientId);
    }

    @GetMapping("/ComandaByClient/{clientId}/{comandaId}")
    Comanda getComandaByClientAndComanda(@PathVariable Long clientId, @PathVariable Long comandaId) {
        return comandaRepository.findComandaByClientIdAndComandaId(clientId, comandaId)
                .orElseThrow(() -> new RuntimeException("Comanda cu acest ID pentru acest client nu exista!"));
    }

    @PutMapping("/Comanda/{comandaId}") // update
    Comanda updateComanda(@RequestBody Comanda comandaNoua, @PathVariable Long comandaId) {
        return comandaRepository.findById(comandaId).map(comanda -> {
            comanda.setComandaId(comandaNoua.getComandaId());
            comanda.setTipPlata(comandaNoua.getTipPlata());
            comanda.setPretTotal(comandaNoua.getPretTotal());
            comanda.setData(comandaNoua.getData());
            comanda.setTarifTara(comandaNoua.getTarifTara());
            comanda.setTarifJudet(comandaNoua.getTarifJudet());
            comanda.setTarifOras(comandaNoua.getTarifOras());
            comanda.setTarifStrada(comandaNoua.getTarifStrada());
            comanda.setTarifNumar(comandaNoua.getTarifNumar());
            comanda.setStatus(comandaNoua.getStatus());
            comanda.setRestaurantId(comandaNoua.getRestaurantId());
            comanda.setClientId(comandaNoua.getClientId());
            comanda.setProduseId(comandaNoua.getProduseId());
            return comandaRepository.save(comanda);
        }).orElseThrow(() -> new RuntimeException("Comanda cu acest Id nu exista!"));
    }

    @DeleteMapping("/Comanda/{comandaId}") // delete
    String deleteComanda(@PathVariable Long comandaId) {
        if (!comandaRepository.existsById(comandaId)) {
            throw new RuntimeException("Comanda cu acest Id nu exista!");
        }
        comandaRepository.deleteById(comandaId);
        return "Comanda cu acest ID a fost stearsa!";
    }

}
