package com.example.Microservice_User_A2.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO extends RepresentationModel<UserDTO> {
    private UUID id_u;
    private String name;
    private String role;
    private String email;
    private String password;
}
