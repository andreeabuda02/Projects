package com.example.Microservice_Device_A1.repositories;

import com.example.Microservice_Device_A1.entities.UserDeviceMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDeviceMappingRepository extends JpaRepository<UserDeviceMapping, UUID> {
}
