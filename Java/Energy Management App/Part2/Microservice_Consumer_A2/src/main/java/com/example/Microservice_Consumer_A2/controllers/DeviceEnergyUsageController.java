package com.example.Microservice_Consumer_A2.controllers;

import com.example.Microservice_Consumer_A2.services.DeviceEnergyUsageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/energy_usage")
public class DeviceEnergyUsageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceEnergyUsageController.class);

    private final DeviceEnergyUsageService deviceEnergyUsageService;

    @Autowired
    public DeviceEnergyUsageController(DeviceEnergyUsageService deviceEnergyUsageService) {
        this.deviceEnergyUsageService = deviceEnergyUsageService;
    }

    @GetMapping()
    public ResponseEntity<String> getStatus() {
        LOGGER.info("Received request to check service status.");
        return ResponseEntity.ok("Device Energy Usage Service is operational.");
    }

    @GetMapping(value = "/{deviceId}/{date}")
    public ResponseEntity<List<Double>> getDeviceEnergyUsageByDeviceIdAndDate(
            @PathVariable("deviceId") UUID deviceId,
            @PathVariable("date") String date) {
        LOGGER.info("Fetching energy usage for device ID: {} on date: {}", deviceId, date);

        List<Double> hourlyEnergyUsage = deviceEnergyUsageService.findDeviceEnergyUsagesByDeviceIdAndDate(deviceId, Date.valueOf(date));
        return new ResponseEntity<>(hourlyEnergyUsage, HttpStatus.OK);
    }
}
