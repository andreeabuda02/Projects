package com.example.ProiectBackendNew.Controller;

import com.example.ProiectBackendNew.Model.Adresa;
import com.example.ProiectBackendNew.Model.Client;
import com.example.ProiectBackendNew.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/Client") //create
    Client createClient(@RequestBody Client clientNou){
        return clientRepository.save(clientNou);
    }

    //toate selecturile
    @GetMapping("/Clients")
    List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @GetMapping("/Client/{clientId}")
    Client getClientById(@PathVariable Long clientId){
        return clientRepository.findById(clientId).orElseThrow(()->new RuntimeException("Clientul cu acest ID nu exista!"));
    }

    @GetMapping("/ClientAdresa/{adresaId}")
    Client getClientByAdresa(@PathVariable Long adresaId){
        return clientRepository.findClientByAdresaId(adresaId).orElseThrow(()->new RuntimeException("Clientul cu aceasta adresa nu exista!"));
    }

    @GetMapping("/ClientEmail/{email}")
    Client getClientByEmail(@PathVariable String email){
        return clientRepository.findClientByEmail(email).orElseThrow(()->new RuntimeException("Clientul cu aceast email nu exista!"));
    }

    @GetMapping("/ClientParola/{parola}")
    Client getClientByParola(@PathVariable String parola){
        return clientRepository.findClientByParola(parola).orElseThrow(()->new RuntimeException("Clientul cu aceasta parola nu exista!"));
    }

    @GetMapping("/ClientByEmailAndParola/{email}/{parola}")
    Client getClientByEmailAndParola(@PathVariable String email, @PathVariable String parola) {
        return clientRepository.findClientByEmailAndParola(email, parola).orElseThrow(() -> new RuntimeException("Clientul cu acest email si parola nu exista!"));
    }

    @PutMapping("/Client/{clientId}") //update
    Client updateClient(@RequestBody Client clientNou, @PathVariable Long clientId){
        return clientRepository.findById(clientId).map(client -> {
            client.setClientId(clientNou.getClientId());
            client.setNume(clientNou.getNume());
            client.setPrenume(clientNou.getPrenume());
            client.setTelefon(clientNou.getTelefon());
            client.setEmail(clientNou.getEmail());
            client.setParola(clientNou.getParola());
            client.setAdresaId(clientNou.getAdresaId());
            return clientRepository.save(client);
        }).orElseThrow(()-> new RuntimeException("Clientul cu acest Id nu exista!"));
    }

    @DeleteMapping("/Client/{clientId}") //delete
    String deleteClient(@PathVariable Long clientId){
        if(clientRepository.existsById(clientId)==false) {
            throw new RuntimeException("Clientul cu acest Id nu exista!");
        }
        clientRepository.deleteById(clientId);
        return "Clientul cu acest ID a fost sters!";
    }
}
