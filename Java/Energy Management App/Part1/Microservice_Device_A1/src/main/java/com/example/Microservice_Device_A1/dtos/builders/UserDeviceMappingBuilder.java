package com.example.Microservice_Device_A1.dtos.builders;

import com.example.Microservice_Device_A1.dtos.UserDeviceMappingDetailsDTO;
import com.example.Microservice_Device_A1.entities.UserDeviceMapping;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDeviceMappingBuilder {
    public static UserDeviceMappingDetailsDTO convertToUserDeviceMappingDetailsDTO(UserDeviceMapping userDeviceMapping){
        return UserDeviceMappingDetailsDTO.builder()
                .userId(userDeviceMapping.getUserId())
                .devicesList(userDeviceMapping.getDevicesList())
                .build();
    }

    public static UserDeviceMapping convertToUserDeviceMapping(UserDeviceMappingDetailsDTO userDeviceMappingDetailsDTO){
        return UserDeviceMapping.builder()
                .userId(userDeviceMappingDetailsDTO.getUserId())
                .devicesList(userDeviceMappingDetailsDTO.getDevicesList())
                .build();
    }
}
