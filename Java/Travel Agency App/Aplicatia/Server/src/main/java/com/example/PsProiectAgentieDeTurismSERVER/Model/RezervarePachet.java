package com.example.PsProiectAgentieDeTurismSERVER.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity
public class RezervarePachet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRezervare;
    private Integer idPachet;
    private Integer idClient;
    private Date dataRezervare;

    public RezervarePachet(Integer idRezervare, Integer idPachet, Integer idClient, Date dataRezervare) {
        this.idRezervare = idRezervare;
        this.idPachet = idPachet;
        this.idClient = idClient;
        this.dataRezervare = dataRezervare;
    }

    public RezervarePachet() {

    }

    public Integer getIdRezervare() {
        return idRezervare;
    }

    public void setIdRezervare(Integer idRezervare) {
        this.idRezervare = idRezervare;
    }

    public Integer getIdPachet() {
        return idPachet;
    }

    public void setIdPachet(Integer idPachet) {
        this.idPachet = idPachet;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Date getDataRezervare() {
        return dataRezervare;
    }

    public void setDataRezervare(Date dataRezervare) {
        this.dataRezervare = dataRezervare;
    }
}
