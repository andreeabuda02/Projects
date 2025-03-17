package com.example.Microservice_Consumer_A3.controllers;

import com.example.Microservice_Consumer_A3.security.JwtValidator;
import com.example.Microservice_Consumer_A3.services.DeviceEnergyUsageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/energy_usage")
public class DeviceEnergyUsageController {  //***
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceEnergyUsageController.class);

    private final JwtValidator jwtValidator;
    private final DeviceEnergyUsageService deviceEnergyUsageService;

    @Autowired
    public DeviceEnergyUsageController(DeviceEnergyUsageService deviceEnergyUsageService, JwtValidator jwtValidator) {
        this.deviceEnergyUsageService = deviceEnergyUsageService;
        this.jwtValidator = jwtValidator;
    }

    @GetMapping()
    public ResponseEntity<String> checkServiceStatus() {
        LOGGER.info("Service health check requested.");
        return ResponseEntity.ok("Device Energy Usage Service is operational.");
    }

    @GetMapping(value = "/{deviceId}/{date}")
    public ResponseEntity<List<Double>> retrieveEnergyUsage(
            @PathVariable("deviceId") UUID deviceId,
            @PathVariable("date") String date,
            @RequestHeader HttpHeaders headers) {

        String token = headers.getFirst("Authorization");
        if (token == null || !jwtValidator.validateToken(token)) {
            LOGGER.warn("Unauthorized access attempt detected for device ID: {}", deviceId);
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNAUTHORIZED);
        }

        LOGGER.info("Fetching energy data for device ID: {} on date: {}", deviceId, date);
        List<Double> energyUsageData = deviceEnergyUsageService.findDeviceEnergyUsagesByDeviceIdAndDate(deviceId, Date.valueOf(date));

        return new ResponseEntity<>(energyUsageData, HttpStatus.OK);
    }
}
