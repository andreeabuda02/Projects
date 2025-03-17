package com.example.Microservice_Consumer_A3.services;

import com.example.Microservice_Consumer_A3.dtos.DeviceEnergyUsageDTO;
import com.example.Microservice_Consumer_A3.dtos.builders.DeviceEnergyUsageBuilder;
import com.example.Microservice_Consumer_A3.entities.DeviceEnergyUsage;
import com.example.Microservice_Consumer_A3.repositories.DeviceEnergyUsageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceEnergyUsageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceEnergyUsageService.class);
    private final DeviceEnergyUsageRepository deviceEnergyUsageRepository;

    @Autowired
    public DeviceEnergyUsageService(DeviceEnergyUsageRepository deviceEnergyUsageRepository) {
        this.deviceEnergyUsageRepository = deviceEnergyUsageRepository;
    }

    @Transactional
    public void saveDeviceEnergyUsage(DeviceEnergyUsageDTO deviceEnergyUsageDTO) {
        DeviceEnergyUsage deviceEnergyUsage = DeviceEnergyUsageBuilder.convertToDeviceEnergyUsageEntity(deviceEnergyUsageDTO);
        deviceEnergyUsage = deviceEnergyUsageRepository.save(deviceEnergyUsage);
        LOGGER.debug("Entry \"{}\" and with device id \"{}\" was inserted in db!", deviceEnergyUsage.getEntryNumber(), deviceEnergyUsage.getDevice().getDeviceId());
    }

    @Transactional
    public List<Double> findDeviceEnergyUsagesByDeviceIdAndDate(UUID deviceId, Date date) {
        List<DeviceEnergyUsage> deviceEnergyUsageList = deviceEnergyUsageRepository.findAllByDevice_DeviceIdAndDate(deviceId, date);

        LOGGER.debug("List of energy usages for device with id \"{}\" and date \"{}\" found!", deviceId, date);

        List<Double> hourlyEnergyUsage = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hourlyEnergyUsage.add(i, 0.0d);
        }
        for (DeviceEnergyUsage deviceEnergyUsage : deviceEnergyUsageList) {
            int hour = deviceEnergyUsage.getTime().toLocalTime().getHour();
            Double newEnergyUsage = deviceEnergyUsage.getEnergyUsed();
            if(newEnergyUsage > hourlyEnergyUsage.get(hour)){
                hourlyEnergyUsage.set(hour, newEnergyUsage);
            }
        }

        return hourlyEnergyUsage;
    }

    @Transactional
    public List<DeviceEnergyUsageDTO> find1DeviceEnergyUsagesByDeviceIdAndDate(UUID deviceId, Date date) {
        List<DeviceEnergyUsage> deviceEnergyUsageList = deviceEnergyUsageRepository.findAllByDevice_DeviceIdAndDate(deviceId, date);

        LOGGER.debug("List of energy usages for device with id \"{}\" and date \"{}\" found!", deviceId, date);

        List<DeviceEnergyUsageDTO> deviceEnergyUsageDTOList = new ArrayList<>();

        for(DeviceEnergyUsage d:deviceEnergyUsageList){
            deviceEnergyUsageDTOList.add(DeviceEnergyUsageBuilder.convertToDeviceEnergyUsageDTO(d));
        }

        return deviceEnergyUsageDTOList;
    }
}

