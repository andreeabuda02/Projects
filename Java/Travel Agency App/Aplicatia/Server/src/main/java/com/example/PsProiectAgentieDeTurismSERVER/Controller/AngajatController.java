package com.example.PsProiectAgentieDeTurismSERVER.Controller;

import com.example.PsProiectAgentieDeTurismSERVER.Model.Angajat;
import com.example.PsProiectAgentieDeTurismSERVER.Service.AngajatService;
import com.example.PsProiectAgentieDeTurismSERVER.Service.EmailService;
import com.example.PsProiectAgentieDeTurismSERVER.Service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")

public class AngajatController {

    @Autowired
    private AngajatService angajatService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private SmsService smsService;

    @PostMapping("/Angajat/creaza")
    public ResponseEntity<Angajat> creazaAngajat(@RequestBody Angajat angajat) {
        Angajat angajatNou = angajatService.creazaAngajat(angajat);
        return new ResponseEntity<>(angajatNou, HttpStatus.CREATED);
    }

    @PutMapping("/Angajat/editeaza/{id}")
    public ResponseEntity<Angajat> editeazaAngajat(@RequestBody Angajat angajat, @PathVariable Integer id) {
        Angajat angajatActualizat = angajatService.editeazaAngajat(angajat, id);
        if (angajatActualizat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            emailService.sendEmail(angajatActualizat.getEmail(),"Date modificate!", emailService.dateModificate1(angajatActualizat));
            smsService.trimiteSms1(angajatActualizat);
            return new ResponseEntity<>(angajatActualizat, HttpStatus.OK);
        }
    }

    @DeleteMapping("/Angajat/sterge/{id}")
    public ResponseEntity<Void> stergeAngajat(@PathVariable Integer id) {
        Integer rezultat = angajatService.stergeAngajat(id);
        if (rezultat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/Angajat/vizualizeazaAngajat/{id}")
    public ResponseEntity<Angajat> vizualizeazaAngajat(@PathVariable Integer id) {
        Angajat angajat = angajatService.vizualizeazaAngajat(id);
        if (angajat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(angajat, HttpStatus.OK);
    }

    @GetMapping("/Angajat/vizualizeazaToti")
    public ResponseEntity<List<Angajat>> vizualizeazaTotiAngajatii() {
        List<Angajat> list = angajatService.vizualizeazaTotiAngajatii();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/Angajat/cautaDupaEmailSiParola/{email}/{parola}")
    public ResponseEntity<Angajat> cautaAngajatDupaEmailSiParola(@PathVariable String email, @PathVariable String parola) {
        Angajat angajat = angajatService.cautaAngajatDupaEmailSiParola(email, parola);
        if (angajat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(angajat, HttpStatus.OK);
    }
}
