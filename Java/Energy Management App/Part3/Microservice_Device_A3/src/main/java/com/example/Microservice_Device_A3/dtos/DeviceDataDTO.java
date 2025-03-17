package com.example.Microservice_Device_A3.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceDataDTO {
    private UUID deviceId;
    private double maximum_hourly_energy_consumption;

}
