package com.example.Microservice_Device_A3.repositories;

import com.example.Microservice_Device_A3.entities.UserDeviceMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDeviceMappingRepository extends JpaRepository<UserDeviceMapping, UUID> {
}
