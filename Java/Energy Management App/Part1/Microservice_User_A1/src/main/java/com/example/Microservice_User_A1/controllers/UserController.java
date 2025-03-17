package com.example.Microservice_User_A1.controllers;

import com.example.Microservice_User_A1.controllers.handlers.excetions.model.DuplicateResourceException;
import com.example.Microservice_User_A1.controllers.handlers.excetions.model.FieldFormatException;
import com.example.Microservice_User_A1.controllers.handlers.excetions.model.ResourceNotFoundException;
import com.example.Microservice_User_A1.dtos.UserDTO;
import com.example.Microservice_User_A1.dtos.UserDetailsDTO;
import com.example.Microservice_User_A1.services.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value="/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final RestTemplate restTemplate;

    @Value("${URL_DEVICE_API:localhost}")
    private String urlDeviceApi;

    @Autowired
    public UserController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<UserDTO> list = userService.findAllUsers();
            for (UserDTO u : list) {
                Link userLink = linkTo(methodOn(UserController.class).getUserById(u.getId_u())).withRel("user_details");
                u.add(userLink);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error fetching all users");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }}

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable("id") UUID id_u) {
        try {
            UserDetailsDTO userDetailsDTO = userService.findUserById(id_u);

            return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, e.getStatus());
        }
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserDetailsDTO> getUserByEmail(@PathVariable("email") String userEmail) {
        try {
            UserDetailsDTO userDetailsDTO = userService.findUserByEmail(userEmail);
            return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("User not found with email: {}", userEmail);
            return new ResponseEntity<>(null, e.getStatus());
        }
    }

    @GetMapping(value = "/{email}/{password}")
    public ResponseEntity<UserDetailsDTO> getUserByEmailAndPassword(@PathVariable("email") String userEmail, @PathVariable("password") String userPassword) {
        try {
            UserDetailsDTO userDetailsDTO = userService.findUserByEmailAndPassword(userEmail, userPassword);
            return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("User not found with email: {} and password: {}", userEmail, userPassword);
            return new ResponseEntity<>(null, e.getStatus());
        }
    }

    @PostMapping()
    public ResponseEntity<UUID> addUser(@Valid @RequestBody UserDetailsDTO userDetailsDTO) {
        UUID userId;
        try {
            userId = userService.addUser(userDetailsDTO);
        } catch (FieldFormatException | DuplicateResourceException e) {
            LOGGER.error("Error saving user!");
            return new ResponseEntity<>(null, e.getStatus());
        }

        try {
            JSONObject userObject = new JSONObject();
            userObject.put("userId", userId);
            userObject.put("devicesList", new JSONArray());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(userObject.toString(), headers);

            String url = urlDeviceApi+"user_device_mapping";
            restTemplate.postForEntity(url, request, String.class);//"http://localhost:8081/user_device_mapping"
            return new ResponseEntity<>(userId, HttpStatus.CREATED);

        } catch (RestClientException | JSONException e) {
            LOGGER.error("Error creating user-device mapping for user ID: {}", userId);
            userService.deleteUser(userId);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") UUID userId) {
        String errorMessage1 = "Error while deleting this user";
        try {
            String url = urlDeviceApi + "user_device_mapping/"+userId;
            restTemplate.delete(url);
        } catch (RestClientException e) {
            return new ResponseEntity<>(errorMessage1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            String deleteResponse = userService.deleteUser(userId);

            return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDetailsDTO> updateUserById(@Valid @RequestBody UserDetailsDTO userDetailsDTO, @PathVariable("id") UUID userId) {
        try {
            UserDetailsDTO updatedUserDetailsDTO = userService.updateUser(userDetailsDTO, userId);
            return new ResponseEntity<>(updatedUserDetailsDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException | FieldFormatException | DuplicateResourceException e) {
            LOGGER.error("Error updating user ID: {}", userId);
            return new ResponseEntity<>(null, e.getStatus());
        }
    }
}