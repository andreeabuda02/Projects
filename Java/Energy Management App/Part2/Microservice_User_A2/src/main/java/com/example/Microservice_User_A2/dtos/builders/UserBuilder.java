package com.example.Microservice_User_A2.dtos.builders;

import com.example.Microservice_User_A2.dtos.UserDTO;
import com.example.Microservice_User_A2.dtos.UserDetailsDTO;
import com.example.Microservice_User_A2.entities.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserBuilder {
    public static UserDTO convertToUserDTO(User user){
        return UserDTO.builder()
                .id_u(user.getId_u())
                .name(user.getName())
                .role(user.getRole())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static UserDetailsDTO convertToUserDetailsDTO(User user){
        return UserDetailsDTO.builder()
                .id_u(user.getId_u())
                .name(user.getName())
                .role(user.getRole())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static User convertToUser(UserDetailsDTO userDetailsDTO){
        return User.builder()
                .id_u(userDetailsDTO.getId_u())
                .name(userDetailsDTO.getName())
                .role(userDetailsDTO.getRole())
                .email(userDetailsDTO.getEmail())
                .password(userDetailsDTO.getPassword())
                .build();
    }

}
