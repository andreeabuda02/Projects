package com.example.PsProiectAgentieDeTurismSERVER.Controller;

import com.example.PsProiectAgentieDeTurismSERVER.Model.Administrator;
import com.example.PsProiectAgentieDeTurismSERVER.Service.AdministratorService;
import com.example.PsProiectAgentieDeTurismSERVER.Service.EmailService;
import com.example.PsProiectAgentieDeTurismSERVER.Service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")

public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private SmsService smsService;

    @PostMapping("/Administrator/creaza")
    public ResponseEntity<Administrator> creazaAdministrator(@RequestBody Administrator administrator){
        Administrator administrator1 = administratorService.creazaAdministrator(administrator);
        return new ResponseEntity<>(administrator1, HttpStatus.CREATED);
    }

    @PutMapping("/Administrator/editeaza/{id}")
    public ResponseEntity<Administrator> editeazaAdministrator(@RequestBody Administrator administrator, @PathVariable Integer id){
        Administrator administrator1 = administratorService.editeazaAdministrator(administrator,id);
        if(administrator1 == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            emailService.sendEmail(administrator1.getEmail(),"Date modificate!", emailService.dateModificate(administrator1));
            smsService.trimiteSms(administrator1);
            return new ResponseEntity<>(administrator1, HttpStatus.OK);
        }
    }

    @DeleteMapping("/Administrator/sterge/{id}")
    public ResponseEntity<Integer> stergeAdministrator(@PathVariable Integer id){
        Integer ok = administratorService.stergeAdministrator(id);
        if(ok == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/Administrator/vizualizeazaAdmin/{id}")
    public ResponseEntity<Administrator> vizualizeazaAdmin(@PathVariable Integer id){
        Administrator administrator = administratorService.vizualizeazaAdmin(id);
        if(administrator == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(administrator, HttpStatus.OK);
        }
    }

    @GetMapping("/Administrator/vizualizeazaToti")
    public ResponseEntity<List<Administrator>> vizualizeazaTotiAdminii(){
        List<Administrator> list = administratorService.vizualizeazaTotiAdminii();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/Administrator/cautaDupaEmailSiParola/{email}/{parola}")
    public ResponseEntity<Administrator> cautaAdminDupaEmailSiParola(@PathVariable String email, @PathVariable String parola){
        Administrator administrator = administratorService.cautaAdminDupaEmailSiParola(email,parola);
        if(administrator == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(administrator, HttpStatus.OK);
    }
}
