package com.example.Microservice_User_A2.dtos;

import com.example.Microservice_User_A2.dtos.validators.ValidName;
import com.example.Microservice_User_A2.dtos.validators.ValidPassword;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDetailsDTO {
    private UUID id_u;
    @NotNull
    @ValidName
    private String name;
    @NotNull
    private String role;
    @NotNull
    private String email;
    @NotNull
    @ValidPassword
    private String password;
}
