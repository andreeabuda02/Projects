package com.example.PsProiectAgentieDeTurismSERVER.Service;

import com.example.PsProiectAgentieDeTurismSERVER.Model.PachetTuristic;
import com.example.PsProiectAgentieDeTurismSERVER.Repository.PachetTuristicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class PachetTuristicService {
    @Autowired
    private PachetTuristicRepository pachetTuristicRepository;

    public PachetTuristic creazaPachetTuristic(PachetTuristic pachetTuristic) {
        return pachetTuristicRepository.save(pachetTuristic);
    }

    public PachetTuristic editeazaPachetTuristic(PachetTuristic pachetTuristic, Integer id) {
        return pachetTuristicRepository.findById(id).map(pachetNou -> {
            pachetNou.setDestinatie(pachetTuristic.getDestinatie());
            pachetNou.setPerioada(pachetTuristic.getPerioada());
            pachetNou.setPret(pachetTuristic.getPret());
            pachetNou.setImagine(pachetTuristic.getImagine());
            return pachetTuristicRepository.save(pachetNou);
        }).orElse(null);
    }

    public Integer stergePachetTuristic(Integer id) {
        if (pachetTuristicRepository.existsById(id)) {
            pachetTuristicRepository.deleteById(id);
            return id;
        } else {
            return null;
        }
    }

    public PachetTuristic vizualizeazaPachetTuristic(Integer id) {
        return pachetTuristicRepository.findById(id).orElse(null);
    }

    public List<PachetTuristic> vizualizeazaToatePacheteleTuristice() {
        return pachetTuristicRepository.findAll(Sort.by(Sort.Direction.ASC, "destinatie","perioada"));
    }

    public List<PachetTuristic> cautaPacheteFiltrateByCriteria(String destinatie, Date perioada, Double pret) {
        Optional<List<PachetTuristic>> pachete;
        if(destinatie.equals("-") && perioada.toString().equals("1000-10-10") && pret < 0){
            pachete= Optional.of(pachetTuristicRepository.findAll(Sort.by(Sort.Direction.ASC, "destinatie","perioada")));
        }
        else{
            boolean p=true;
            boolean d=true;
            boolean pr=true;

            if(destinatie.equals("-")){
                d=false;
            }
            if (perioada.toString().equals("1000-10-10")){
                p=false;
            }
            if(pret < 0 ){
                pr=false;
            }

            if(d && p && pr) {
                pachete = pachetTuristicRepository.findPachetTuristicsByDestinatieAndPerioadaAndPretIsLessThanEqualOrderByDestinatieAscPerioadaAsc(destinatie, perioada, pret);
            }
            else {
                if(d && p) {
                    pachete = pachetTuristicRepository.findPachetTuristicsByDestinatieAndPerioadaOrderByDestinatieAscPerioadaAsc(destinatie, perioada);
                }
                else {
                    if(d) {
                       pachete = pachetTuristicRepository.findPachetTuristicsByDestinatieOrderByDestinatieAscPerioadaAsc(destinatie);
                    }
                    else {
                        if(p) {
                            pachete = pachetTuristicRepository.findPachetTuristicsByPerioadaOrderByDestinatieAscPerioadaAsc(perioada);
                        }
                        else {
                            pachete = pachetTuristicRepository.findPachetTuristicsByPretIsLessThanEqualOrderByDestinatieAscPerioadaAsc(pret);
                        }
                    }
                }
            }
        }
        return pachete.orElse(null);
    }
}
