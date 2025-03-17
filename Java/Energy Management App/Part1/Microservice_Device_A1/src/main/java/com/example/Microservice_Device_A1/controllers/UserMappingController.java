package com.example.Microservice_Device_A1.controllers;

import com.example.Microservice_Device_A1.controllers.handlers.exceptions.ResourceNotFoundException;
import com.example.Microservice_Device_A1.dtos.UserDeviceMappingDetailsDTO;
import com.example.Microservice_Device_A1.services.DeviceService;
import com.example.Microservice_Device_A1.services.UserDeviceMappingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value="/user_device_mapping")
public class UserMappingController {
    private final UserDeviceMappingService userDeviceMappingService;

    private final DeviceService deviceService;


    public UserMappingController(UserDeviceMappingService userDeviceMappingService, DeviceService deviceService) {
        this.userDeviceMappingService = userDeviceMappingService;
        this.deviceService = deviceService;
    }

    @PostMapping()
    public ResponseEntity<UUID> addUserDeviceMapping(@Valid @RequestBody UserDeviceMappingDetailsDTO userDeviceMappingDetailsDTO) {
        UUID userId = userDeviceMappingService.addUserDeviceMapping(userDeviceMappingDetailsDTO);

        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserMapping(@PathVariable("id") UUID userId) {
        try {
            deviceService.updateDevicesByUserId(userId);
            String mesaj = userDeviceMappingService.deleteUserDeviceMapping(userId);
            return new ResponseEntity<>(mesaj, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }
}
