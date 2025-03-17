package com.example.Microservice_Device_A2.repositories;

import com.example.Microservice_Device_A2.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByUser_UserId(UUID userId);
}
