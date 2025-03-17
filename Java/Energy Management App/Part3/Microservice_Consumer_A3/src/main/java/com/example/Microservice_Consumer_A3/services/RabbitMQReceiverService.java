package com.example.Microservice_Consumer_A3.services;

import com.example.Microservice_Consumer_A3.configurations.handlers.exceptions.ResourceNotFoundException;
import com.example.Microservice_Consumer_A3.dtos.DeviceDetailsDTO;
import com.example.Microservice_Consumer_A3.dtos.DeviceDetailsRequestDTO;
import com.example.Microservice_Consumer_A3.dtos.DeviceEnergyUsageDTO;
import com.example.Microservice_Consumer_A3.dtos.MeasurementRequestDTO;
import com.example.Microservice_Consumer_A3.entities.DeviceDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RabbitMQReceiverService {
    private final DeviceDetailsService deviceDetailsService;
    private final DeviceEnergyUsageService deviceEnergyUsageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQReceiverService.class);
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public RabbitMQReceiverService(DeviceDetailsService deviceDetailsService, DeviceEnergyUsageService deviceEnergyUsageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.deviceDetailsService = deviceDetailsService;
        this.deviceEnergyUsageService = deviceEnergyUsageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    private boolean hasExceededMaxHourlyConsumption(List<DeviceEnergyUsageDTO> energyUsageList, double maxHourlyConsumption, double currentMeasurement, int currentHour) {
        double previousHourConsumption = 0.0d;

        if (currentHour > 0) {
            for (DeviceEnergyUsageDTO energyUsage : energyUsageList) {
                if (energyUsage.getTime().toLocalTime().getHour() == (currentHour - 1) && energyUsage.getEnergyUsed() > previousHourConsumption) {
                    previousHourConsumption = energyUsage.getEnergyUsed();
                }
            }
        }

        for (DeviceEnergyUsageDTO energyUsage : energyUsageList) {
            if (energyUsage.getTime().toLocalTime().getHour() == currentHour) {
                if ((currentMeasurement - previousHourConsumption) > maxHourlyConsumption) {
                    return true;
                }
            }
        }

        return false;
    }


    @RabbitListener(queues = "sensor_measurements_queue")
    public void consumeSensorMeasurements(MeasurementRequestDTO measurementDetails) throws ParseException {
        System.out.println(measurementDetails.toString());

        Date date = new Date(measurementDetails.getTimestamp());
        Time time = new Time(measurementDetails.getTimestamp());

        DeviceDetails deviceInfo = deviceDetailsService.findByDeviceDetailsId(measurementDetails.getDevice_id());
        DeviceEnergyUsageDTO deviceEnergyUsageDTO = new DeviceEnergyUsageDTO(0, date, time, measurementDetails.getMeasurement_value(), deviceInfo);
        deviceEnergyUsageService.saveDeviceEnergyUsage(deviceEnergyUsageDTO);

        List<DeviceEnergyUsageDTO> deviceEnergyUsageDTOList = deviceEnergyUsageService.find1DeviceEnergyUsagesByDeviceIdAndDate(measurementDetails.getDevice_id(),date);
        if(hasExceededMaxHourlyConsumption(deviceEnergyUsageDTOList, deviceInfo.getMaximum_hourly_energy_consumption(), measurementDetails.getMeasurement_value(), time.toLocalTime().getHour())){
            simpMessagingTemplate.convertAndSend("/topic/consumeOverflow",
                    "For the device: " + deviceInfo.getDeviceId() + " you have exceeded the " + deviceInfo.getMaximum_hourly_energy_consumption()+ " limit!");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "device_create_update_queue")
    public void consumeDeviceDetails(DeviceDetailsRequestDTO deviceDetailsRequestDTO) {
        DeviceDetailsDTO deviceDetailsDTO = new DeviceDetailsDTO(deviceDetailsRequestDTO.getDeviceId(), deviceDetailsRequestDTO.getMaximum_hourly_energy_consumption(), new ArrayList<>());
        deviceDetailsService.saveOrUpdateDeviceDetails(deviceDetailsDTO);
    }

    @RabbitListener(queues = "device_delete_queue")
    public void consumeDeviceDelete(UUID device_id) {
        try{
            deviceDetailsService.deleteDeviceDetailsById(device_id);
        }catch (ResourceNotFoundException exception){
            LOGGER.error(exception.getMessage());
        }
    }
}
