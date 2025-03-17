package com.example.PsProiectAgentieDeTurismSERVER.Model;

import jakarta.persistence.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

@Entity
public class PachetTuristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPachet;
    private String destinatie;
    private Date perioada;
    private Double pret;
    @Lob
    @Column(length = 1000)
    private byte[] imagine;

    public PachetTuristic(Integer idPachet, String destinatie, Date perioada, Double pret, byte[] imagine) {
        this.idPachet = idPachet;
        this.destinatie = destinatie;
        this.perioada = perioada;
        this.pret = pret;
        this.imagine = imagine;
    }

    public PachetTuristic() {

    }

    public Integer getIdPachet() {
        return idPachet;
    }

    public void setIdPachet(Integer idPachet) {
        this.idPachet = idPachet;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public Date getPerioada() {
        return perioada;
    }

    public void setPerioada(Date perioada) {
        this.perioada = perioada;
    }

    public Double getPret() {
        return pret;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public byte[] getImagine() {
        return imagine;
    }

    public void setImagine(byte[] imagine) {
        this.imagine = imagine;
    }
}
