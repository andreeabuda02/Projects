package com.example.PsProiectAgentieDeTurismSERVER.Controller;

import com.example.PsProiectAgentieDeTurismSERVER.Model.FileFactory.*;
import com.example.PsProiectAgentieDeTurismSERVER.Model.PachetTuristic;
import com.example.PsProiectAgentieDeTurismSERVER.Service.PachetTuristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")

public class FisiereCotroller {
    @Autowired
    private PachetTuristicService pachetTuristicService;


    @GetMapping("/Fisier/{tipFisier}")
    public String creareFisier(@PathVariable String tipFisier){
        List<PachetTuristic> pachetTuristicList = pachetTuristicService.vizualizeazaToatePacheteleTuristice();

        DocumentFactory factory = null;
        switch (tipFisier) {
            case "CSV" -> { factory = new FactoryCSV();  }
            case "JSON" -> { factory = new FactoryJSON();  }
            case "XML" -> { factory = new FactoryXML();  }
            case "DOC" -> { factory = new FactoryDOC(); }
            default -> { System.out.println("Nu este o alegere valida!");}
        }

        if (factory != null) {
            //System.out.println("Salveaza mesaj");
            DocumentWriter scriere = factory.factoryMethod();
            scriere.writeFile(pachetTuristicList);
            //System.out.println("Mesaj deschidere");
            //scriere.openFile();
            return "Salvare si Deschidere fisier!";
        }
        else {
            return "Eroare la deschidere fisierelui!";
            //System.out.println("Eroare la deschidere fisierelui!");
        }
    }
}
