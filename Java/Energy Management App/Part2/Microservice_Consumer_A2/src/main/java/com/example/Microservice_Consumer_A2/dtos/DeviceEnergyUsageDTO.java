package com.example.Microservice_Consumer_A2.dtos;

import com.example.Microservice_Consumer_A2.dtos.validators.ValidEnergyUsage;
import com.example.Microservice_Consumer_A2.entities.DeviceDetails;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DeviceEnergyUsageDTO extends RepresentationModel<DeviceEnergyUsageDTO> {
    private long entryNumber;
    private Date date;
    private Time time;
    @ValidEnergyUsage
    private double energyUsed;
    private DeviceDetails device;
}
