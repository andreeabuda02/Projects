package com.example.ProiectBackendNew.Model;

import jakarta.persistence.*;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;
    private String nume;
    private String oraDeschidere;
    private String oraInchidere;

    private Long adresaId;

    public Restaurant() {
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getOraDeschidere() {
        return oraDeschidere;
    }

    public void setOraDeschidere(String oraDeschidere) {
        this.oraDeschidere = oraDeschidere;
    }

    public String getOraInchidere() {
        return oraInchidere;
    }

    public void setOraInchidere(String oraInchidere) {
        this.oraInchidere = oraInchidere;
    }

    public Long getAdresaId() {
        return adresaId;
    }

    public void setAdresaId(Long adresaId) {
        this.adresaId = adresaId;
    }

    public Restaurant(Long restaurantId, String nume, String oraDeschidere, String oraInchidere, Long adresaId) {
        this.restaurantId = restaurantId;
        this.nume = nume;
        this.oraDeschidere = oraDeschidere;
        this.oraInchidere = oraInchidere;
        this.adresaId = adresaId;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                ", nume='" + nume + '\'' +
                ", oraDeschidere='" + oraDeschidere + '\'' +
                ", oraInchidere='" + oraInchidere + '\'' +
                ", adresaId=" + adresaId +
                '}';
    }
}
