package com.example.ProiectBackendNew.Model;

import jakarta.persistence.*;

@Entity
public class Adresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adresaId;
    private String tara;
    private String judetul;
    private String orasul;
    private String strada;
    private String numarul;

    public Adresa() {
    }

    public Adresa(Long adresaId, String tara, String judetul, String orasul, String strada, String numarul) {
        this.adresaId = adresaId;
        this.tara = tara;
        this.judetul = judetul;
        this.orasul = orasul;
        this.strada = strada;
        this.numarul = numarul;
    }

    public Long getAdresaId() {
        return adresaId;
    }

    public void setAdresaId(Long adresaId) {
        this.adresaId = adresaId;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getJudetul() {
        return judetul;
    }

    public void setJudetul(String judetul) {
        this.judetul = judetul;
    }

    public String getOrasul() {
        return orasul;
    }

    public void setOrasul(String orasul) {
        this.orasul = orasul;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNumarul() {
        return numarul;
    }

    public void setNumarul(String numarul) {
        this.numarul = numarul;
    }

    @Override
    public String toString() {
        return "Adresa{" +
                "adresaId=" + adresaId +
                ", tara='" + tara + '\'' +
                ", judetul='" + judetul + '\'' +
                ", orasul='" + orasul + '\'' +
                ", strada='" + strada + '\'' +
                ", numarul='" + numarul + '\'' +
                '}';
    }
}
