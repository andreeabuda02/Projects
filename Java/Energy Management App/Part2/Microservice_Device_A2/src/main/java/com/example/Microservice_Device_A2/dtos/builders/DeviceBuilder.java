package com.example.Microservice_Device_A2.dtos.builders;

import com.example.Microservice_Device_A2.dtos.DeviceDTO;
import com.example.Microservice_Device_A2.dtos.DeviceDetailsDTO;
import com.example.Microservice_Device_A2.entities.Device;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeviceBuilder {
    public static DeviceDTO convertToDeviceDTO(Device device){
        return DeviceDTO.builder()
                .id_d(device.getId_d())
                .description(device.getDescription())
                .address(device.getAddress())
                .maximum_hourly_energy_consumption(device.getMaximum_hourly_energy_consumption())
                .userDeviceMapping(device.getUser())
                .build();
    }

    public static DeviceDetailsDTO convertToDeviceDetailsDTO(Device device){
        return DeviceDetailsDTO.builder()
                .id_d(device.getId_d())
                .description(device.getDescription())
                .address(device.getAddress())
                .maximum_hourly_energy_consumption(device.getMaximum_hourly_energy_consumption())
                .userDeviceMapping(device.getUser())
                .build();
    }

    public static DeviceDetailsDTO convertToDeviceDetailsDTO(DeviceDTO deviceDTO){
        return DeviceDetailsDTO.builder()
                .id_d(deviceDTO.getId_d())
                .description(deviceDTO.getDescription())
                .address(deviceDTO.getAddress())
                .maximum_hourly_energy_consumption(deviceDTO.getMaximum_hourly_energy_consumption())
                .userDeviceMapping(deviceDTO.getUserDeviceMapping())
                .build();
    }

    public static Device convertToDevice(DeviceDetailsDTO deviceDetailsDTO){
        return Device.builder()
                .id_d(deviceDetailsDTO.getId_d())
                .description(deviceDetailsDTO.getDescription())
                .address(deviceDetailsDTO.getAddress())
                .maximum_hourly_energy_consumption(deviceDetailsDTO.getMaximum_hourly_energy_consumption())
                .user(deviceDetailsDTO.getUserDeviceMapping())
                .build();
    }

}
