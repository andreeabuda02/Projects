package com.example.Microservice_Device_A1.dtos;

import com.example.Microservice_Device_A1.dtos.validators.ValidEnergyConsumption;
import com.example.Microservice_Device_A1.entities.UserDeviceMapping;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DeviceDetailsDTO {
    private UUID id_d;
    @NotNull
    private String description;
    @NotNull
    private String address;
    @ValidEnergyConsumption
    @NotNull
    private double maximum_hourly_energy_consumption;
    private UserDeviceMapping userDeviceMapping;
}
