package com.example.Microservice_Device_A2.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@Table(name="user_device_mapping")
@AllArgsConstructor
@NoArgsConstructor
public class UserDeviceMapping implements Serializable {
    @Id
    private UUID userId;
    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Device> devicesList;
}
