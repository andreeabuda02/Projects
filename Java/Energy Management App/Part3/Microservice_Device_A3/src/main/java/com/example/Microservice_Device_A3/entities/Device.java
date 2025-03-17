package com.example.Microservice_Device_A3.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="device_table")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Device implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_d;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Positive(message = "Max hourly consumption must be a positive value")
    @Column(name = "maximum_hourly_energy_consumption", nullable = false)
    private double maximum_hourly_energy_consumption;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserDeviceMapping user;
}
