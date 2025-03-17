package com.example.Microservice_Device_A1.services;

import com.example.Microservice_Device_A1.controllers.handlers.exceptions.EntityValidationException;
import com.example.Microservice_Device_A1.controllers.handlers.exceptions.ResourceNotFoundException;
import com.example.Microservice_Device_A1.dtos.DeviceDTO;
import com.example.Microservice_Device_A1.dtos.DeviceDetailsDTO;
import com.example.Microservice_Device_A1.dtos.builders.DeviceBuilder;
import com.example.Microservice_Device_A1.entities.Device;
import com.example.Microservice_Device_A1.repositories.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public List<DeviceDTO> findAllDevices(){
        List<Device> deviceList;
        deviceList = deviceRepository.findAll();
        List<DeviceDTO> deviceDTOList = new ArrayList<>();
        for (Device d: deviceList) {
            deviceDTOList.add(DeviceBuilder.convertToDeviceDTO(d));
        }
        LOGGER.info("Retrieved all devices from the database.");
        return deviceDTOList;
    }

    @Transactional
    public DeviceDetailsDTO findDeviceById(UUID id) throws ResourceNotFoundException {
        Optional<Device> device = deviceRepository.findById(id);
        if(device.isEmpty()){
            LOGGER.warn("Device with ID \"{}\" not found.", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " device with id: " + id);
        }
        LOGGER.info("Device with ID \"{}\" retrieved successfully.", id);
        return DeviceBuilder.convertToDeviceDetailsDTO(device.get());
    }

    @Transactional
    public String deleteDevice(UUID id) throws ResourceNotFoundException {
        Optional<Device> device = deviceRepository.findById(id);
        if(device.isEmpty()){
            LOGGER.warn("Attempted to delete non-existent device with ID \"{}\".", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " device with id: " + id);
        }
        deviceRepository.deleteById(id);
        LOGGER.info("Device with ID \"{}\" deleted successfully from the database.", id);
        return "Device with ID " + id + " has been deleted.";
    }

    @Transactional
    public UUID addDevice(DeviceDetailsDTO deviceDetailsDTO) {
        if (deviceDetailsDTO.getMaximum_hourly_energy_consumption() < 0) {
            throw new EntityValidationException("Invalid data", Map.of("maximum_hourly_energy_consumption", "must be positive"));
        }
        Device device = DeviceBuilder.convertToDevice(deviceDetailsDTO);
        device = deviceRepository.save(device);
        LOGGER.info("Device with ID \"{}\" has been added to the database.", device.getId_d());
        return device.getId_d();
    }


    @Transactional
    public DeviceDetailsDTO updateDevice(DeviceDetailsDTO deviceDetailsDTO, UUID id) {
        if (deviceDetailsDTO.getMaximum_hourly_energy_consumption() < 0) {
            throw new EntityValidationException("Invalid data",
                    Map.of("maximum_hourly_energy_consumption", "must be positive"));
        }
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isEmpty()) {
            LOGGER.warn("Device with ID \"{}\" not found for update.", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with ID: " + id);
        }
        Device deviceU = device.map(d -> {
            d.setId_d(deviceDetailsDTO.getId_d());
            d.setDescription(deviceDetailsDTO.getDescription());
            d.setAddress(deviceDetailsDTO.getAddress());
            d.setMaximum_hourly_energy_consumption(deviceDetailsDTO.getMaximum_hourly_energy_consumption());
            d.setUser(deviceDetailsDTO.getUserDeviceMapping());
            return deviceRepository.save(d);
        }).get();
        LOGGER.info("Device with ID \"{}\" updated successfully.", id);
        return DeviceBuilder.convertToDeviceDetailsDTO(deviceU);
    }

    public void updateDevicesByUserId(UUID userId) {
        List<DeviceDTO> deviceDTOList = findDevicesByUserId(userId);
        for(DeviceDTO deviceDTO: deviceDTOList) {
            DeviceDetailsDTO deviceDetailsDTO = DeviceBuilder.convertToDeviceDetailsDTO(deviceDTO);
            deviceDetailsDTO.setUserDeviceMapping(null);
            updateDevice(deviceDetailsDTO, deviceDetailsDTO.getId_d());
        }
    }

    @Transactional
    public List<DeviceDTO> findDevicesByUserId(UUID id) {
        List<Device> deviceList = deviceRepository.findByUser_UserId(id);
        List<DeviceDTO> deviceDTOList = new ArrayList<>();
        deviceList.forEach(device -> deviceDTOList.add(DeviceBuilder.convertToDeviceDTO(device)));
        LOGGER.info("All devices associated with user ID \"{}\" retrieved successfully.", id);
        return deviceDTOList;
    }
}
