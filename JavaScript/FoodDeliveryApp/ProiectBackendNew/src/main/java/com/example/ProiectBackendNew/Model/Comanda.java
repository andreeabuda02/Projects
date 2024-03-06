package com.example.ProiectBackendNew.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Comanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comandaId;
    private String tipPlata;
    private Double pretTotal;
    private String data;
    private Double tarifTara;
    private Double tarifJudet;
    private Double tarifOras;
    private Double tarifStrada;
    private Double tarifNumar;
    private String status;

    private Long restaurantId;

    private Long clientId;

    private String produseId; //Excemplu ce o sa fie aici: 1_10_2_13


    public Comanda() {
    }

    public Comanda(Long comandaId, String tipPlata, Double pretTotal, String data, Double tarifTara, Double tarifJudet, Double tarifOras, Double tarifStrada, Double tarifNumar, String status, Long restaurantId, Long clientId, String produseId) {
        this.comandaId = comandaId;
        this.tipPlata = tipPlata;
        this.pretTotal = pretTotal;
        this.data = data;
        this.tarifTara = tarifTara;
        this.tarifJudet = tarifJudet;
        this.tarifOras = tarifOras;
        this.tarifStrada = tarifStrada;
        this.tarifNumar = tarifNumar;
        this.status = status;
        this.restaurantId = restaurantId;
        this.clientId = clientId;
        this.produseId = produseId;
    }

    public Long getComandaId() {
        return comandaId;
    }

    public void setComandaId(Long comandaId) {
        this.comandaId = comandaId;
    }

    public String getTipPlata() {
        return tipPlata;
    }

    public void setTipPlata(String tipPlata) {
        this.tipPlata = tipPlata;
    }

    public Double getPretTotal() {
        return pretTotal;
    }

    public void setPretTotal(Double pretTotal) {
        this.pretTotal = pretTotal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getTarifTara() {
        return tarifTara;
    }

    public void setTarifTara(Double tarifTara) {
        this.tarifTara = tarifTara;
    }

    public Double getTarifJudet() {
        return tarifJudet;
    }

    public void setTarifJudet(Double tarifJudet) {
        this.tarifJudet = tarifJudet;
    }

    public Double getTarifOras() {
        return tarifOras;
    }

    public void setTarifOras(Double tarifOras) {
        this.tarifOras = tarifOras;
    }

    public Double getTarifStrada() {
        return tarifStrada;
    }

    public void setTarifStrada(Double tarifStrada) {
        this.tarifStrada = tarifStrada;
    }

    public Double getTarifNumar() {
        return tarifNumar;
    }

    public void setTarifNumar(Double tarifNumar) {
        this.tarifNumar = tarifNumar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getProduseId() {
        return produseId;
    }

    public void setProduseId(String produseId) {
        this.produseId = produseId;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "comandaId=" + comandaId +
                ", tipPlata='" + tipPlata + '\'' +
                ", pretTotal=" + pretTotal +
                ", data='" + data + '\'' +
                ", tarifTara=" + tarifTara +
                ", tarifJudet=" + tarifJudet +
                ", tarifOras=" + tarifOras +
                ", tarifStrada=" + tarifStrada +
                ", tarifNumar=" + tarifNumar +
                ", status='" + status + '\'' +
                ", restaurantId=" + restaurantId +
                ", clientId=" + clientId +
                ", produseId='" + produseId + '\'' +
                '}';
    }
}
