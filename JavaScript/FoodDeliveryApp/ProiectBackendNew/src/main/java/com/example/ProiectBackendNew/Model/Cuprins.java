package com.example.ProiectBackendNew.Model;

import jakarta.persistence.*;

@Entity
public class Cuprins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuprinsId;

    private Long produsId;

    private Long restaurantId;

    public Cuprins() {
    }

    public Long getCuprinsId() {
        return cuprinsId;
    }

    public void setCuprinsId(Long cuprinsId) {
        this.cuprinsId = cuprinsId;
    }

    public Long getProdusId() {
        return produsId;
    }

    public void setProdusId(Long produsId) {
        this.produsId = produsId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long comandaId) {
        this.restaurantId = comandaId;
    }

    public Cuprins(Long cuprinsId, Long produsId, Long restaurantId) {
        this.cuprinsId = cuprinsId;
        this.produsId = produsId;
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "Cuprins{" +
                "cuprinsId=" + cuprinsId +
                ", produsId=" + produsId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
