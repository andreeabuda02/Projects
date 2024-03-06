package com.example.ProiectBackendNew.Controller;

import com.example.ProiectBackendNew.Model.Administrator;
import com.example.ProiectBackendNew.Model.Client;
import com.example.ProiectBackendNew.Repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:3000")
public class AdministratorController {

    @Autowired
    private AdministratorRepository administratorRepository;

    @PostMapping("/Administrator") // create
    Administrator createAdministrator(@RequestBody Administrator administratorNou) {
        return administratorRepository.save(administratorNou);
    }

    // toate selecturile
    @GetMapping("/Administratori")
    List<Administrator> getAllAdministratori() {
        return administratorRepository.findAll();
    }

    @GetMapping("/Administrator/{administratorId}")
    Administrator getAdministratorById(@PathVariable Long administratorId) {
        return administratorRepository.findById(administratorId)
                .orElseThrow(() -> new RuntimeException("Administratorul cu acest ID nu exista!"));
    }

    @GetMapping("/AdministratorByUsername/{username}")
    Administrator getAdministratorByUsername(@PathVariable String username) {
        return administratorRepository.findAdministratorByUsername(username)
                .orElseThrow(() -> new RuntimeException("Administratorul cu acest username nu exista!"));
    }

    @GetMapping("/AdministratorByPassword/{password}")
    Administrator getAdministratorByPassword(@PathVariable String password) {
        return administratorRepository.findAdministratorByPassword(password)
                .orElseThrow(() -> new RuntimeException("Administratorul cu acesta parola nu exista!"));
    }

    @GetMapping("/AdminByEmailAndParola/{username}/{password}")
    Administrator getAdminByEmailAndParola(@PathVariable String username, @PathVariable String password) {
        return administratorRepository.findAdministratorByUsernameAndPassword(username, password).orElseThrow(() -> new RuntimeException("Administratorul cu acest email si parola nu exista!"));
    }

    @PutMapping("/Administrator/{administratorId}") // update
    Administrator updateAdministrator(@RequestBody Administrator administratorNou, @PathVariable Long administratorId) {
        return administratorRepository.findById(administratorId).map(administrator -> {
            administrator.setAdministratorId(administratorNou.getAdministratorId());
            administrator.setUsername(administratorNou.getUsername());
            administrator.setPassword(administratorNou.getPassword());
            return administratorRepository.save(administrator);
        }).orElseThrow(() -> new RuntimeException("Administratorul cu acest Id nu exista!"));
    }

    @DeleteMapping("/Administrator/{administratorId}") // delete
    String deleteAdministrator(@PathVariable Long administratorId) {
        if (!administratorRepository.existsById(administratorId)) {
            throw new RuntimeException("Administratorul cu acest Id nu exista!");
        }
        administratorRepository.deleteById(administratorId);
        return "Administratorul cu acest ID a fost sters!";
    }

}
