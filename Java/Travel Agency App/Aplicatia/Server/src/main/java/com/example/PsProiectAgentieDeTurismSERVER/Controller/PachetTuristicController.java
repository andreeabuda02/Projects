package com.example.PsProiectAgentieDeTurismSERVER.Controller;

import com.example.PsProiectAgentieDeTurismSERVER.Model.PachetTuristic;
import com.example.PsProiectAgentieDeTurismSERVER.Service.PachetTuristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class PachetTuristicController {
    @Autowired
    private PachetTuristicService pachetTuristicService;

    @PostMapping("/PachetTuristic/creaza")
    public ResponseEntity<PachetTuristic> creazaPachetTuristic(@RequestBody PachetTuristic pachetTuristic) {
        PachetTuristic pachetNou = pachetTuristicService.creazaPachetTuristic(pachetTuristic);
        if (pachetNou != null) {
            return new ResponseEntity<>(pachetNou, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/PachetTuristic/editeaza/{idPachet}")
    public ResponseEntity<PachetTuristic> editeazaPachetTuristic(@RequestBody PachetTuristic pachetTuristic, @PathVariable Integer idPachet) {
        PachetTuristic pachetEditat = pachetTuristicService.editeazaPachetTuristic(pachetTuristic, idPachet);
        if (pachetEditat != null) {
            return new ResponseEntity<>(pachetEditat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/PachetTuristic/sterge/{idPachet}")
    public ResponseEntity<Void> stergePachetTuristic(@PathVariable Integer idPachet) {
        Integer idSters = pachetTuristicService.stergePachetTuristic(idPachet);
        if (idSters != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/PachetTuristic/vizualizeazaPachetTuristic/{idPachet}")
    public ResponseEntity<PachetTuristic> vizualizeazaPachetTuristic(@PathVariable Integer idPachet) {
        PachetTuristic pachetTuristic = pachetTuristicService.vizualizeazaPachetTuristic(idPachet);
        if (pachetTuristic != null) {
            return new ResponseEntity<>(pachetTuristic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/PachetTuristic/vizualizeazaToate")
    public ResponseEntity<List<PachetTuristic>> vizualizeazaToatePacheteleTuristice() {
        List<PachetTuristic> pacheteTuristice = pachetTuristicService.vizualizeazaToatePacheteleTuristice();
        if (!pacheteTuristice.isEmpty()) {
            return new ResponseEntity<>(pacheteTuristice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/PachetTuristic/filtreazaDupaCriteriu/{destinatie}/{perioada}/{pret}")
    public ResponseEntity<List<PachetTuristic>> cautaPacheteFiltrateByCriteria(@PathVariable String destinatie, @PathVariable Date perioada, @PathVariable Double pret) {
        List<PachetTuristic> pacheteTuristice = pachetTuristicService.cautaPacheteFiltrateByCriteria(destinatie, perioada, pret);
        if (!pacheteTuristice.isEmpty()) {
            return new ResponseEntity<>(pacheteTuristice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}