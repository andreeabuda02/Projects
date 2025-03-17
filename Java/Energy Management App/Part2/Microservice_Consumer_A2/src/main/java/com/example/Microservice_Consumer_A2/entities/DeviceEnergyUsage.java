package com.example.Microservice_Consumer_A2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "device_energy_usage")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeviceEnergyUsage {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long entryNumber;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "time", nullable = false)
    private Time time;
    @Column(name = "energy_used", nullable = false)
    private double energyUsed;
    @ManyToOne
    @JoinColumn(name = "device_id")
    private DeviceDetails device;
}
