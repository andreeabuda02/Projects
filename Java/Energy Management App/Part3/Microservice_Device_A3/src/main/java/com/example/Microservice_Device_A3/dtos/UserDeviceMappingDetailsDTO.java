package com.example.Microservice_Device_A3.dtos;

import com.example.Microservice_Device_A3.entities.Device;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDeviceMappingDetailsDTO {
    @NotNull
    private UUID userId;
    private List<Device> devicesList;
}
