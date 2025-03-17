package com.example.Microservice_Consumer_A2.services;

import com.example.Microservice_Consumer_A2.configurations.handlers.exceptions.ResourceNotFoundException;
import com.example.Microservice_Consumer_A2.dtos.DeviceDetailsDTO;
import com.example.Microservice_Consumer_A2.dtos.builders.DeviceDetailsBuilder;
import com.example.Microservice_Consumer_A2.entities.DeviceDetails;
import com.example.Microservice_Consumer_A2.repositories.DeviceDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceDetailsService {  
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceDetailsService.class);
    private final DeviceDetailsRepository deviceDetailsRepository;  

    @Autowired
    public DeviceDetailsService(DeviceDetailsRepository deviceDetailsRepository) {
        this.deviceDetailsRepository = deviceDetailsRepository;
    }

    @Transactional
    public DeviceDetails findByDeviceDetailsId(UUID deviceId) {
        LOGGER.info("Searching for device with ID: {}", deviceId);

        Optional<DeviceDetails> optionalDeviceDetails = deviceDetailsRepository.findById(deviceId);
        if (optionalDeviceDetails.isEmpty()) {
            String errorMessage = String.format("No device found with the specified ID: %s", deviceId);
            LOGGER.error(errorMessage);
            throw new ResourceNotFoundException(DeviceDetails.class.getSimpleName() + " device with id: " + deviceId);
        }
        return optionalDeviceDetails.get();
    }

    @Transactional
    public void saveOrUpdateDeviceDetails(DeviceDetailsDTO deviceDetailsDTO) {
        LOGGER.info("Saving or updating device with ID: {}", deviceDetailsDTO.getDeviceId());

        Optional<DeviceDetails> existingDevice = deviceDetailsRepository.findById(deviceDetailsDTO.getDeviceId());
        DeviceDetails deviceDetails = DeviceDetailsBuilder.convertToDeviceDetailsEntity(deviceDetailsDTO);

        if (existingDevice.isPresent()) {
            LOGGER.debug("Updating device with ID: {}", deviceDetailsDTO.getDeviceId());
            deviceDetails.setDeviceEnergyUsageList(existingDevice.get().getDeviceEnergyUsageList());
        } else {
            LOGGER.debug("Creating new device entry with ID: {}", deviceDetailsDTO.getDeviceId());
        }

        deviceDetailsRepository.save(deviceDetails);
        LOGGER.info("Device with ID {} has been successfully saved or updated.", deviceDetailsDTO.getDeviceId());
    }

    @Transactional
    public void deleteDeviceDetailsById(UUID deviceId) throws ResourceNotFoundException {
        LOGGER.info("Attempting to delete device with ID: {}", deviceId);

        Optional<DeviceDetails> device = deviceDetailsRepository.findById(deviceId);

        if (device.isEmpty()) {
            String errorMessage = String.format("Deletion failed. Device with ID %s does not exist.", deviceId);
            LOGGER.error(errorMessage);
            throw new ResourceNotFoundException(DeviceDetails.class.getSimpleName() + " device with id: " + deviceId);
        }

        deviceDetailsRepository.deleteById(deviceId);
        LOGGER.info("Device with ID {} has been successfully deleted.", deviceId);
    }
}

