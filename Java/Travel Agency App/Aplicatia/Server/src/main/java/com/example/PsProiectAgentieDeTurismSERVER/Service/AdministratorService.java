package com.example.PsProiectAgentieDeTurismSERVER.Service;

import com.example.PsProiectAgentieDeTurismSERVER.Model.Administrator;
import com.example.PsProiectAgentieDeTurismSERVER.Repository.AdministratorRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator creazaAdministrator(Administrator administrator){
        Administrator administrator1 = administratorRepository.save(administrator);
        return administrator1;
    }

    public Administrator editeazaAdministrator(Administrator administrator, Integer id){
        Optional<Administrator> administrator1 = administratorRepository.findById(id).map(administratorNou -> {
            administratorNou.setEmail(administrator.getEmail());
            administratorNou.setParola(administrator.getParola());
            administratorNou.setTelefon(administrator.getTelefon());
            administratorNou.setNume(administrator.getNume());
            administratorNou.setPrenume(administrator.getPrenume());

            return administratorRepository.save(administratorNou);
        });
        if(administrator1.isPresent()){
            return administrator1.get();
        }
        else {
            return null;
        }
    }

    public Integer stergeAdministrator(Integer id) {
        if(administratorRepository.existsById(id)){
            administratorRepository.deleteById(id);
            return id;
        }
        else{
            return null;
        }
    }

    public List<Administrator> vizualizeazaTotiAdminii(){
        List<Administrator> list = administratorRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        return list;
    }

    public Administrator cautaAdminDupaEmailSiParola(String email, String parola){
        Optional<Administrator> administrator = administratorRepository.findByEmailAndParola(email, parola);
        if(administrator.isPresent()){
            return administrator.get();
        }
        else{
            return null;
        }
    }

    public Administrator vizualizeazaAdmin(Integer id){
        Optional<Administrator> administrator = administratorRepository.findById(id);
        if(administrator.isPresent()){
            return administrator.get();
        }
        else{
            return null;
        }
    }
}
