package com.example.Microservice_Device_A3.services;

import com.example.Microservice_Device_A3.controllers.handlers.exceptions.ResourceNotFoundException;
import com.example.Microservice_Device_A3.dtos.UserDeviceMappingDetailsDTO;
import com.example.Microservice_Device_A3.dtos.builders.UserDeviceMappingBuilder;
import com.example.Microservice_Device_A3.entities.UserDeviceMapping;
import com.example.Microservice_Device_A3.repositories.UserDeviceMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserDeviceMappingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDeviceMappingService.class);
    private final UserDeviceMappingRepository userDeviceMappingRepository;

    public UserDeviceMappingService(UserDeviceMappingRepository userDeviceMappingRepository) {
        this.userDeviceMappingRepository = userDeviceMappingRepository;
    }

    @Transactional
    public UUID addUserDeviceMapping(UserDeviceMappingDetailsDTO userDeviceMappingDetailsDTO) {
        UserDeviceMapping userMapping = UserDeviceMappingBuilder.convertToUserDeviceMapping(userDeviceMappingDetailsDTO);

        userMapping = userDeviceMappingRepository.save(userMapping);
        LOGGER.debug("UserMapping with id \"{}\" was inserted in db!", userMapping.getUserId());

        return userMapping.getUserId();
    }

    @Transactional
    public String deleteUserDeviceMapping(UUID userId) throws ResourceNotFoundException {
        Optional<UserDeviceMapping> optionalUserMapping = userDeviceMappingRepository.findById(userId);
        if(optionalUserMapping.isEmpty()) {
            LOGGER.warn("User-device mapping with user ID \"{}\" not found for deletion.", userId);
            throw new ResourceNotFoundException(UserDeviceMapping.class.getSimpleName() + " with user ID: " + userId);
        }
        userDeviceMappingRepository.deleteById(userId);
        LOGGER.info("User-device mapping for user ID \"{}\" has been deleted from the database.", userId);
        return "User-device mapping for user ID \"" + userId + "\" has been removed.";
    }

}
