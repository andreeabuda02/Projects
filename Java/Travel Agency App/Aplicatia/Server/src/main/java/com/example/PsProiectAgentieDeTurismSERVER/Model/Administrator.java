package com.example.PsProiectAgentieDeTurismSERVER.Model;

import jakarta.persistence.Entity;

@Entity
public class Administrator extends Utilizator{

    private String email;
    private String parola;
    private String telefon;


    public Administrator(Integer id, String nume, String prenume, String email, String telefon, String parola) {
        super(id, nume, prenume);
        this.telefon=telefon;
        this.email=email;
        this.parola=parola;
    }

    public Administrator() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
