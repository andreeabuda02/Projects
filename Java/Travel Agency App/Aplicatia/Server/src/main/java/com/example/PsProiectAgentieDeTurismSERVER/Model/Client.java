package com.example.PsProiectAgentieDeTurismSERVER.Model;

import jakarta.persistence.Entity;

@Entity
public class Client extends Utilizator{
    public Client(Integer id, String nume, String prenume) {
        super(id, nume, prenume);
    }

    public Client() {
        super();
    }
}
