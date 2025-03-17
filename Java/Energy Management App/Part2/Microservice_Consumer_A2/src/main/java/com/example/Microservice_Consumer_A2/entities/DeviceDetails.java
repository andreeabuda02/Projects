package com.example.Microservice_Consumer_A2.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "device_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeviceDetails implements Serializable {
    @Id
    private UUID deviceId;
    @Column(name = "maximum_hourly_energy_consumption", nullable = false)
    private double maximum_hourly_energy_consumption;
    @OneToMany(mappedBy = "device", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<DeviceEnergyUsage> deviceEnergyUsageList;

}
