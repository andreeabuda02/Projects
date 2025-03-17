package com.example.PsProiectAgentieDeTurismSERVER.Service;

import com.example.PsProiectAgentieDeTurismSERVER.Model.Client;
import com.example.PsProiectAgentieDeTurismSERVER.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client creazaClient(Client client) {
        return clientRepository.save(client);
    }

    public Client editeazaClient(Client client, Integer id) {
        return clientRepository.findById(id).map(clientNou -> {
            clientNou.setNume(client.getNume());
            clientNou.setPrenume(client.getPrenume());
            return clientRepository.save(clientNou);
        }).orElse(null);
    }

    public Integer stergeClient(Integer id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return id;
        } else {
            return null;
        }
    }

    public List<Client> vizualizeazaTotiClientii() {
        return clientRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Client vizualizeazaClient(Integer id) {
        return clientRepository.findById(id).orElse(null);
    }
}
