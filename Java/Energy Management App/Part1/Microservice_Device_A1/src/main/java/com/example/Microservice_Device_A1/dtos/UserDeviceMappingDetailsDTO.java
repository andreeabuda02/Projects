package com.example.Microservice_Device_A1.dtos;

import com.example.Microservice_Device_A1.entities.Device;
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
