package com.example.ProiectBackendNew.Controller;


import com.example.ProiectBackendNew.Model.Adresa;
import com.example.ProiectBackendNew.Repository.AdresaRepository;
import com.example.ProiectBackendNew.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class AdresaController {

    @Autowired
    private AdresaRepository adresaRepository;

    @PostMapping("/Adresa") // create
    Adresa createAdresa(@RequestBody Adresa adresaNoua) {
        return adresaRepository.save(adresaNoua);
    }

    // toate selecturile
    @GetMapping("/Adrese")
    List<Adresa> getAllAdrese() {
        return adresaRepository.findAll();
    }

    @GetMapping("/Adresa/{adresaId}")
    Adresa getAdresaById(@PathVariable Long adresaId) {
        return adresaRepository.findById(adresaId).orElseThrow(() -> new RuntimeException("Adresa cu acest ID nu exista!"));
    }

    @GetMapping("/AdresaByOrasSiStrada/{orasul}/{strada}")
    Adresa getAdresaByOrasSiStrada(@PathVariable String oras, @PathVariable String strada) {
        return adresaRepository.findAdresaByOrasulAndStrada(oras, strada).orElseThrow(() -> new RuntimeException("Adresa din acest oras si strada nu exista!"));
    }

    @GetMapping("/AdresaByTaraSiJudet/{tara}/{judetul}")
    Adresa getAdresaByTaraSiJudet(@PathVariable String tara, @PathVariable String judet) {
        return adresaRepository.findAdresaByTaraAndJudetul(tara, judet).orElseThrow(() -> new RuntimeException("Adresa din aceasta tara si județ nu exista!"));
    }

    @GetMapping("/AdresaByTaraAndJudetulAndOrasulAndStradaAndNumarul/{tara}/{judetul}/{orasul}/{strada}/{numarul}")
    Adresa getAdresaByTaraAndJudetulAndOrasulAndStradaAndNumarul(@PathVariable String tara, @PathVariable String judetul, @PathVariable String orasul, @PathVariable String strada, @PathVariable String numarul) {
        return adresaRepository.findAdresaByTaraAndJudetulAndOrasulAndStradaAndNumarul(tara, judetul, orasul, strada, numarul).orElseThrow(() -> new RuntimeException("Adresa aceasta nu exista!"));
    }

    @PutMapping("/Adresa/{adresaId}") // update
    Adresa updateAdresa(@RequestBody Adresa adresaNoua, @PathVariable Long adresaId) {
        return adresaRepository.findById(adresaId).map(adresa -> {
            adresa.setAdresaId(adresaNoua.getAdresaId());
            adresa.setTara(adresaNoua.getTara());
            adresa.setJudetul(adresaNoua.getJudetul());
            adresa.setOrasul(adresaNoua.getOrasul());
            adresa.setStrada(adresaNoua.getStrada());
            adresa.setNumarul(adresaNoua.getNumarul());
            return adresaRepository.save(adresa);
        }).orElseThrow(() -> new RuntimeException("Adresa cu acest Id nu exista!"));
    }

    @DeleteMapping("/Adresa/{adresaId}") // delete
    String deleteAdresa(@PathVariable Long adresaId) {
        if (!adresaRepository.existsById(adresaId)) {
            throw new RuntimeException("Adresa cu acest Id nu exista!");
        }
        adresaRepository.deleteById(adresaId);
        return "Adresa cu acest ID a fost stearsa!";
    }
}

