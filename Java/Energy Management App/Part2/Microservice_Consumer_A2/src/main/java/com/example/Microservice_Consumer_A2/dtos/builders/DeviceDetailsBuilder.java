package com.example.Microservice_Consumer_A2.dtos.builders;

import com.example.Microservice_Consumer_A2.dtos.DeviceDetailsDTO;
import com.example.Microservice_Consumer_A2.entities.DeviceDetails;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeviceDetailsBuilder {
    public static DeviceDetailsDTO convertToDeviceDetailsDTO(DeviceDetails deviceInfo) {
        return DeviceDetailsDTO.builder()
                .deviceId(deviceInfo.getDeviceId())
                .maximum_hourly_energy_consumption(deviceInfo.getMaximum_hourly_energy_consumption())
                .deviceEnergyUsageList(deviceInfo.getDeviceEnergyUsageList())
                .build();
    }

    public static DeviceDetails convertToDeviceDetailsEntity(DeviceDetailsDTO deviceInfoDTO) {
        return DeviceDetails.builder()
                .deviceId(deviceInfoDTO.getDeviceId())
                .maximum_hourly_energy_consumption(deviceInfoDTO.getMaximum_hourly_energy_consumption())
                .deviceEnergyUsageList(deviceInfoDTO.getDeviceEnergyUsageList())
                .build();
    }
}
