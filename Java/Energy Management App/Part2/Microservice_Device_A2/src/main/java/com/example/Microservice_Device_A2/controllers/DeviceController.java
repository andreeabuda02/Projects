package com.example.Microservice_Device_A2.controllers;

import com.example.Microservice_Device_A2.configurations.RabbitMQSender;
import com.example.Microservice_Device_A2.controllers.handlers.exceptions.ResourceNotFoundException;
import com.example.Microservice_Device_A2.dtos.DeviceDTO;
import com.example.Microservice_Device_A2.dtos.DeviceDataDTO;
import com.example.Microservice_Device_A2.dtos.DeviceDetailsDTO;
import com.example.Microservice_Device_A2.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value="/device")
public class DeviceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);

    private final DeviceService deviceService;
    private final RabbitMQSender rabbitMQSender;


    @Autowired
    public DeviceController(DeviceService deviceService, RabbitMQSender rabbitMQSender){
        this.deviceService=deviceService;
        this.rabbitMQSender = rabbitMQSender;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getAllDevices(){
        LOGGER.info("Fetching all devices.");
        List<DeviceDTO> list = deviceService.findAllDevices();
        for(DeviceDTO d:list){
            Link deviceLink = linkTo(methodOn(DeviceController.class).getDeviceById(d.getId_d())).withRel("device_details");
            d.add(deviceLink);
        }
        LOGGER.debug("Total devices fetched: {}", list.size());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDetailsDTO> getDeviceById(@PathVariable("id") UUID id_d){
        LOGGER.info("Fetching device by ID: {}", id_d);
        try{
            DeviceDetailsDTO device = deviceService.findDeviceById(id_d);
            return new ResponseEntity<>(device, HttpStatus.OK);
        }catch (ResourceNotFoundException exception){
            LOGGER.error("Device with ID {} not found.", id_d);
            return new ResponseEntity<>(null, exception.getStatus());
        }
    }

    @PostMapping()
    public ResponseEntity<UUID> addDevice(@Valid @RequestBody DeviceDetailsDTO deviceDetailsDTO) {
        LOGGER.info("Adding new device: {}", deviceDetailsDTO.getDescription());
        UUID id_device = deviceService.addDevice(deviceDetailsDTO);
        DeviceDataDTO deviceDataDTO = new DeviceDataDTO(id_device, deviceDetailsDTO.getMaximum_hourly_energy_consumption());
        rabbitMQSender.sendForCreateUpdateDevice(deviceDataDTO);
        LOGGER.debug("Device added with ID: {}", id_device);
        return new ResponseEntity<>(id_device, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") UUID id_d) {
        LOGGER.info("Deleting device by ID: {}", id_d);
        try{
            String mesaj = deviceService.deleteDevice(id_d);
            rabbitMQSender.sendForDeleteDevice(id_d);
            LOGGER.debug("Device deleted: {}", id_d);
            return new ResponseEntity<>(mesaj, HttpStatus.OK);
        }catch (ResourceNotFoundException exception){
            LOGGER.error("Device with ID {} not found for deletion.", id_d);
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DeviceDetailsDTO> updateDevice(@Valid @RequestBody DeviceDetailsDTO deviceDetailsDTO, @PathVariable("id") UUID id_d) {
        LOGGER.info("Updating device by ID: {}", id_d);
        try{
            DeviceDetailsDTO device = deviceService.updateDevice(deviceDetailsDTO, id_d);
            DeviceDataDTO deviceDataDTO = new DeviceDataDTO(id_d, deviceDetailsDTO.getMaximum_hourly_energy_consumption());
            rabbitMQSender.sendForCreateUpdateDevice(deviceDataDTO);
            LOGGER.debug("Device updated: {}", id_d);
            return new ResponseEntity<>(device, HttpStatus.OK);
        }catch (ResourceNotFoundException exception){
            LOGGER.error("Device with ID {} not found for update.", id_d);
            return new ResponseEntity<>(null, exception.getStatus());
        }
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<DeviceDTO>> getDeviceByUserId(@PathVariable("id") UUID userId){
        LOGGER.info("Fetching devices for user ID: {}", userId);
        List<DeviceDTO> deviceDTOList = deviceService.findDevicesByUserId(userId);
        for (DeviceDTO deviceDto : deviceDTOList) {
            Link deviceLink = linkTo(methodOn(DeviceController.class).getDeviceById(deviceDto.getId_d())).withRel("deviceDetails");
            deviceDto.add(deviceLink);
        }
        LOGGER.debug("Devices fetched for user ID {}: {}", userId, deviceDTOList.size());
        return new ResponseEntity<>(deviceDTOList, HttpStatus.OK);
    }

}
