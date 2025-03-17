package com.example.Microservice_Consumer_A3.repositories;


import com.example.Microservice_Consumer_A3.entities.DeviceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceDetailsRepository extends JpaRepository<DeviceDetails, UUID> {
}
