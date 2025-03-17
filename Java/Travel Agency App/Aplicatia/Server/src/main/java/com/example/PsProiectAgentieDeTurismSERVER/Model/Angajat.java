package com.example.PsProiectAgentieDeTurismSERVER.Model;

import jakarta.persistence.Entity;

@Entity
public class Angajat extends Utilizator{

    private String post;
    private String email;
    private String parola;
    private String telefon;


    public Angajat(Integer id, String nume, String prenume, String email, String telefon, String parola, String post) {
        super(id, nume, prenume);
        this.post=post;
        this.email=email;
        this.parola=parola;
        this.telefon=telefon;
    }

    public Angajat() {
        super();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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
