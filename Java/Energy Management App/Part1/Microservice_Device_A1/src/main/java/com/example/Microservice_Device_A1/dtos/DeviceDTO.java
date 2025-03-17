package com.example.Microservice_Device_A1.dtos;

import com.example.Microservice_Device_A1.dtos.validators.ValidEnergyConsumption;
import com.example.Microservice_Device_A1.entities.UserDeviceMapping;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private UUID id_d;
    private String description;
    private String address;
    @ValidEnergyConsumption
    private double maximum_hourly_energy_consumption;
    private UserDeviceMapping userDeviceMapping;
}
