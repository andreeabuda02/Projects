package com.example.ProiectBackendNew.Model;

import jakarta.persistence.*;

@Entity
public class Produs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produsId;
    private String nume;
    private Double pret;

    public Produs(Long produsId, String nume, Double pret) {
        this.produsId = produsId;
        this.nume = nume;
        this.pret = pret;
    }

    public Produs() {
    }

    public Long getProdusId() {
        return produsId;
    }

    public void setProdusId(Long produsId) {
        this.produsId = produsId;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Double getPret() {
        return pret;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "produsId=" + produsId +
                ", nume='" + nume + '\'' +
                ", pret=" + pret +
                '}';
    }
}
