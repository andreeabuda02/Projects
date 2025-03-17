package com.example.Microservice_Device_A3.controllers;

import com.example.Microservice_Device_A3.controllers.handlers.exceptions.ResourceNotFoundException;
import com.example.Microservice_Device_A3.dtos.UserDeviceMappingDetailsDTO;
import com.example.Microservice_Device_A3.security.JwtValidator;
import com.example.Microservice_Device_A3.services.DeviceService;
import com.example.Microservice_Device_A3.services.UserDeviceMappingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/user_device_mapping")
public class UserMappingController {
    private final UserDeviceMappingService userDeviceMappingService;
    private final DeviceService deviceService;
    private final JwtValidator jwtValidator;

    public UserMappingController(UserDeviceMappingService userDeviceMappingService, DeviceService deviceService, JwtValidator jwtValidator) {
        this.userDeviceMappingService = userDeviceMappingService;
        this.deviceService = deviceService;
        this.jwtValidator = jwtValidator;
    }

    @PostMapping("/d")
    public ResponseEntity<UUID> createUserDeviceMapping(
            @Valid @RequestBody UserDeviceMappingDetailsDTO userDeviceMappingDetailsDTO) {

        UUID userId = userDeviceMappingService.addUserDeviceMapping(userDeviceMappingDetailsDTO);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<UUID> addUserDeviceMapping(
            @Valid @RequestBody UserDeviceMappingDetailsDTO userDeviceMappingDetailsDTO,
            @RequestHeader HttpHeaders headers) {

        if (!jwtValidator.validateToken(headers.getFirst("Authorization"))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UUID userId = userDeviceMappingService.addUserDeviceMapping(userDeviceMappingDetailsDTO);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserMapping(
            @PathVariable("id") UUID userId,
            @RequestHeader HttpHeaders headers) {

        if (!jwtValidator.validateToken(headers.getFirst("Authorization"))) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }

        try {
            deviceService.updateDevicesByUserId(userId);
            String message = userDeviceMappingService.deleteUserDeviceMapping(userId);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }
}
