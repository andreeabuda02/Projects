package com.example.Microservice_Consumer_A2.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MeasurementRequestDTO {
    private long timestamp;
    private UUID device_id;
    private double measurement_value;
}
