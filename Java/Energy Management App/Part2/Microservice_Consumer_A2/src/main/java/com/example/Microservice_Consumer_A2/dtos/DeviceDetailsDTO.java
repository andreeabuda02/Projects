package com.example.Microservice_Consumer_A2.dtos;

import com.example.Microservice_Consumer_A2.entities.DeviceEnergyUsage;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeviceDetailsDTO {
    private UUID deviceId;
    private double maximum_hourly_energy_consumption;
    private List<DeviceEnergyUsage> deviceEnergyUsageList;
}
