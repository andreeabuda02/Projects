package com.example.Microservice_Consumer_A3.repositories;

import com.example.Microservice_Consumer_A3.entities.DeviceEnergyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceEnergyUsageRepository extends JpaRepository<DeviceEnergyUsage, UUID> {
    List<DeviceEnergyUsage> findAllByDevice_DeviceIdAndDate(UUID deviceEnergyUsageId, Date date);
}
