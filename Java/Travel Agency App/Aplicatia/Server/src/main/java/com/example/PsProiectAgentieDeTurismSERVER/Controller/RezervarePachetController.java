package com.example.PsProiectAgentieDeTurismSERVER.Controller;

import com.example.PsProiectAgentieDeTurismSERVER.Model.PachetTuristic;
import com.example.PsProiectAgentieDeTurismSERVER.Model.RezervarePachet;
import com.example.PsProiectAgentieDeTurismSERVER.Service.RezervarePachetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")

public class RezervarePachetController {

    @Autowired
    private RezervarePachetService rezervarePachetService;

    @PostMapping("/RezervarePachet/creaza")
    public ResponseEntity<RezervarePachet> creazaRezervarePachet(@RequestBody RezervarePachet rezervarePachet) {
        RezervarePachet rezervareNoua = rezervarePachetService.creazaRezervarePachet(rezervarePachet);
        return new ResponseEntity<>(rezervareNoua, HttpStatus.CREATED);
    }

    @PutMapping("/RezervarePachet/editeaza")
    public ResponseEntity<RezervarePachet> editeazaRezervarePachet(@RequestBody RezervarePachet rezervarePachet) {
        RezervarePachet rezervareEditata = rezervarePachetService.editeazaRezervarePachet(rezervarePachet);
        if (rezervareEditata != null) {
            return new ResponseEntity<>(rezervareEditata, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/RezervarePachet/sterge/{id}")
    public ResponseEntity<RezervarePachet> stergeRezervarePachet(@PathVariable Integer id) {
        RezervarePachet rezervare = rezervarePachetService.stergeRezervarePachet(id);
        if (rezervare != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/RezervarePachet/vizualizeaza/{id}")
    public ResponseEntity<RezervarePachet> vizualizeazaRezervarePachetById(@PathVariable Integer id) {
        RezervarePachet rezervarePachet = rezervarePachetService.vizualizeazaRezervarePachetById(id);
        if (rezervarePachet != null) {
            return new ResponseEntity<>(rezervarePachet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/RezervarePachet/vizualizeazaToate")
    public ResponseEntity<List<RezervarePachet>> vizualizeazaAllRezervariPacheteTuristiceOrderedById() {
        List<RezervarePachet> rezervari = rezervarePachetService.vizualizeazaAllRezervariPacheteTuristiceOrderedById();
        return new ResponseEntity<>(rezervari, HttpStatus.OK);
    }

    @GetMapping("/RezervarePachet/cautaDupaClientSiPachet/{idClient}/{idPachet}")
    public ResponseEntity<RezervarePachet> cautaRezervareDupaClientSiPachet(@PathVariable Integer idClient, @PathVariable Integer idPachet) {
        RezervarePachet rezervare = rezervarePachetService.cautaRezervareDupaClientSiPachet(idClient, idPachet);
        if (rezervare != null) {
            return new ResponseEntity<>(rezervare, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/RezervarePachet/cautaDupaClient/{idClient}")
    public ResponseEntity<List<RezervarePachet>> cautaRezervareDupaClient(@PathVariable Integer idClient) {
        List<RezervarePachet> rezervare = rezervarePachetService.cautaRezervareDupaClient(idClient);
        if (rezervare != null) {
            return new ResponseEntity<>(rezervare, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/RezervarePachet/cautaDupaPerioada/{perioada}")
    public ResponseEntity<List<PachetTuristic>> cautaRezervareDupaPerioada(@PathVariable Date perioada) {
        List<PachetTuristic> pachet = rezervarePachetService.cautaRezervareDupaPerioada(perioada);
        if (pachet != null) {
            return new ResponseEntity<>(pachet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/RezervarePachet/cautaDupaPachet/{idPachet}")
    public ResponseEntity<List<RezervarePachet>> cautaRezervareDupaPachet(@PathVariable Integer idPachet) {
        List<RezervarePachet> rezervare = rezervarePachetService.cautaRezervareDupaPachet(idPachet);
        if (rezervare != null) {
            return new ResponseEntity<>(rezervare, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}