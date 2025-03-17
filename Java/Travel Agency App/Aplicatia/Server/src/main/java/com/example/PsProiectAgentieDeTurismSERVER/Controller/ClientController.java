package com.example.PsProiectAgentieDeTurismSERVER.Controller;

import com.example.PsProiectAgentieDeTurismSERVER.Model.Client;
import com.example.PsProiectAgentieDeTurismSERVER.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")

public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/Client/creaza")
    public ResponseEntity<Client> adaugaClient(@RequestBody Client client) {
        Client clientNou = clientService.creazaClient(client);
        return new ResponseEntity<>(clientNou, HttpStatus.CREATED);
    }

    @PutMapping("/Client/editeaza/{id}")
    public ResponseEntity<Client> editeazaClient(@RequestBody Client client, @PathVariable Integer id) {
        Client clientEditat = clientService.editeazaClient(client, id);
        if (clientEditat != null) {
            return new ResponseEntity<>(clientEditat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Client/sterge/{id}")
    public ResponseEntity<Void> stergeClient(@PathVariable Integer id) {
        Integer idSters = clientService.stergeClient(id);
        if (idSters != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Client/vizualizeazaClient/{id}")
    public ResponseEntity<Client> vizualizeazaClient(@PathVariable Integer id) {
        Client client = clientService.vizualizeazaClient(id);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Client/vizualizeazaToti")
    public ResponseEntity<List<Client>> vizualizeazaTotiClientii() {
        List<Client> clienti = clientService.vizualizeazaTotiClientii();
        return new ResponseEntity<>(clienti, HttpStatus.OK);
    }

}