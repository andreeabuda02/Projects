package com.example.Microservice_Consumer_A3.dtos.builders;

import com.example.Microservice_Consumer_A3.dtos.DeviceEnergyUsageDTO;
import com.example.Microservice_Consumer_A3.entities.DeviceEnergyUsage;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeviceEnergyUsageBuilder {
    public static DeviceEnergyUsageDTO convertToDeviceEnergyUsageDTO(DeviceEnergyUsage deviceEnergyUsage) {
        return DeviceEnergyUsageDTO.builder()
                .entryNumber(deviceEnergyUsage.getEntryNumber())
                .date(deviceEnergyUsage.getDate())
                .time(deviceEnergyUsage.getTime())
                .energyUsed(deviceEnergyUsage.getEnergyUsed())
                .device(deviceEnergyUsage.getDevice())
                .build();
    }

    public static DeviceEnergyUsage convertToDeviceEnergyUsageEntity(DeviceEnergyUsageDTO deviceEnergyUsageDTO) {
        return DeviceEnergyUsage.builder()
                .entryNumber(deviceEnergyUsageDTO.getEntryNumber())
                .date(deviceEnergyUsageDTO.getDate())
                .time(deviceEnergyUsageDTO.getTime())
                .energyUsed(deviceEnergyUsageDTO.getEnergyUsed())
                .device(deviceEnergyUsageDTO.getDevice())
                .build();
    }
}
