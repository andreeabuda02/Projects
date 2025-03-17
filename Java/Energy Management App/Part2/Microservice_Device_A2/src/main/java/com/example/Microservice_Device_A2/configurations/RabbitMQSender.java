package com.example.Microservice_Device_A2.configurations;

import com.example.Microservice_Device_A2.dtos.DeviceDataDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendForCreateUpdateDevice(DeviceDataDTO deviceDataDTO) {
        rabbitTemplate.convertAndSend("device_create_update_queue", deviceDataDTO);
    }

    public void sendForDeleteDevice(UUID device_id) {
        rabbitTemplate.convertAndSend("device_delete_queue", device_id);
    }
}
