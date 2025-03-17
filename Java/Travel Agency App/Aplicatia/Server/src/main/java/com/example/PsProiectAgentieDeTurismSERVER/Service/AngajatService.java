package com.example.PsProiectAgentieDeTurismSERVER.Service;

import com.example.PsProiectAgentieDeTurismSERVER.Model.Angajat;
import com.example.PsProiectAgentieDeTurismSERVER.Repository.AngajatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AngajatService {
    @Autowired
    private AngajatRepository angajatRepository;

    public Angajat creazaAngajat(Angajat angajat) {
        return angajatRepository.save(angajat);
    }

    public Angajat editeazaAngajat(Angajat angajat, Integer id) {
        return angajatRepository.findById(id).map(angajatNou -> {
            angajatNou.setEmail(angajat.getEmail());
            angajatNou.setParola(angajat.getParola());
            angajatNou.setTelefon(angajat.getTelefon());
            angajatNou.setNume(angajat.getNume());
            angajatNou.setPrenume(angajat.getPrenume());
            angajatNou.setPost(angajat.getPost());
            return angajatRepository.save(angajatNou);
        }).orElse(null);
    }

    public Integer stergeAngajat(Integer id) {
        if (angajatRepository.existsById(id)) {
            angajatRepository.deleteById(id);
            return id;
        } else {
            return null;
        }
    }

    public List<Angajat> vizualizeazaTotiAngajatii() {
        return angajatRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Angajat cautaAngajatDupaEmailSiParola(String email, String parola) {
        return angajatRepository.findByEmailAndParola(email, parola).orElse(null);
    }

    public Angajat vizualizeazaAngajat(Integer id) {
        return angajatRepository.findById(id).orElse(null);
    }
}
