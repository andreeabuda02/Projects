package com.example.PsProiectAgentieDeTurismSERVER.Service;

import com.example.PsProiectAgentieDeTurismSERVER.Model.PachetTuristic;
import com.example.PsProiectAgentieDeTurismSERVER.Model.RezervarePachet;
import com.example.PsProiectAgentieDeTurismSERVER.Repository.PachetTuristicRepository;
import com.example.PsProiectAgentieDeTurismSERVER.Repository.RezervarePachetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RezervarePachetService {

    @Autowired
    private RezervarePachetRepository rezervarePachetRepository;
    @Autowired
    private PachetTuristicRepository pachetTuristicRepository;

    public RezervarePachet creazaRezervarePachet(RezervarePachet rezervarePachet) {
        return rezervarePachetRepository.save(rezervarePachet);
    }

    public RezervarePachet editeazaRezervarePachet(RezervarePachet rezervarePachet) {
        Integer id = rezervarePachet.getIdRezervare();
        Optional<RezervarePachet> rezervareExistent = rezervarePachetRepository.findById(id);

        if (rezervareExistent.isPresent()) {
            RezervarePachet rezervareActualizata = rezervareExistent.get();
            rezervareActualizata.setIdPachet(rezervarePachet.getIdPachet());
            rezervareActualizata.setIdClient(rezervarePachet.getIdClient());
            rezervareActualizata.setDataRezervare(rezervarePachet.getDataRezervare());
            return rezervarePachetRepository.save(rezervareActualizata);
        } else {
            return null;
        }
    }

    public RezervarePachet stergeRezervarePachet(Integer idRezervarePachet) {
        RezervarePachet rezervare = rezervarePachetRepository.findById(idRezervarePachet).orElse(null);
        if (rezervare != null) {
            rezervarePachetRepository.deleteById(idRezervarePachet);
        }
        return rezervare;
    }

    public RezervarePachet vizualizeazaRezervarePachetById(Integer idRezervarePachet) {
        return rezervarePachetRepository.findById(idRezervarePachet).orElse(null);
    }

    public List<RezervarePachet> vizualizeazaAllRezervariPacheteTuristiceOrderedById() {
        return rezervarePachetRepository.findAll();
    }

    public RezervarePachet cautaRezervareDupaClientSiPachet(Integer idClient, Integer idPachet){
        Optional<RezervarePachet> rezervarePachet = rezervarePachetRepository.findRezervarePachetByIdPachetAndIdClient(idClient, idPachet);
        if(rezervarePachet.isPresent()){
            return rezervarePachet.get();
        }
        else{
            return null;
        }
    }

    public List<RezervarePachet> cautaRezervareDupaClient(Integer idClient){
        Optional<List<RezervarePachet>> rezervarePachet = rezervarePachetRepository.findRezervarePachetsByIdClient(idClient);
        if(rezervarePachet.isPresent()){
            return rezervarePachet.get();
        }
        else{
            return null;
        }
    }

    public List<RezervarePachet> cautaRezervareDupaPachet(Integer idPachet){
        Optional<List<RezervarePachet>> rezervarePachet = rezervarePachetRepository.findRezervarePachetsByIdPachet(idPachet);
        if(rezervarePachet.isPresent()){
            return rezervarePachet.get();
        }
        else{
            return null;
        }
    }

    public List<PachetTuristic> cautaRezervareDupaPerioada(Date perioda) {
        Optional<List<RezervarePachet>> lista = rezervarePachetRepository.findRezervarePachetByDataRezervare(perioda);
        if(lista.isPresent()){
            List<PachetTuristic> pppp =new ArrayList<>();
            for(RezervarePachet r:lista.get()){
                Optional<PachetTuristic> p = pachetTuristicRepository.findById(r.getIdPachet());
                if(p.isPresent()){
                    pppp.add(p.get());
                }
            }
            return pppp;
        }
        return null;
    }
}
