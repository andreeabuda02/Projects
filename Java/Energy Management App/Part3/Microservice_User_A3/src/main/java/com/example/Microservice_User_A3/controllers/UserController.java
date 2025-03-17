package com.example.Microservice_User_A3.controllers;

import com.example.Microservice_User_A3.controllers.handlers.excetions.model.DuplicateResourceException;
import com.example.Microservice_User_A3.controllers.handlers.excetions.model.FieldFormatException;
import com.example.Microservice_User_A3.controllers.handlers.excetions.model.ResourceNotFoundException;
import com.example.Microservice_User_A3.dtos.UserDTO;
import com.example.Microservice_User_A3.dtos.UserDetailsDTO;
import com.example.Microservice_User_A3.security.JwtValidator;
import com.example.Microservice_User_A3.services.UserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final JwtValidator jwtValidator;

    @Value("${URL_DEVICE_API:localhost}")
    private String urlDeviceApi;

    @Autowired
    public UserController(UserService userService, RestTemplate restTemplate, JwtValidator jwtValidator) {
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.jwtValidator = jwtValidator;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestHeader HttpHeaders headers) {
        try {
            if (!jwtValidator.validateToken(headers.getFirst("Authorization"))) {
                LOGGER.warn("Unauthorized attempt to fetch all users.");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            List<UserDTO> list = userService.findAllUsers();
            for (UserDTO u : list) {
                Link userLink = linkTo(methodOn(UserController.class).getUserById(u.getId_u(), headers)).withRel("user_details");
                u.add(userLink);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error fetching all users", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable("id") UUID id, @RequestHeader HttpHeaders headers) {
        try {
            if (!jwtValidator.validateToken(headers.getFirst("Authorization"))) {
                LOGGER.warn("Unauthorized attempt to fetch user by ID.");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            UserDetailsDTO userDetailsDTO = userService.findUserById(id);
            return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("User not found with ID: {}", id);
            return new ResponseEntity<>(null, e.getStatus());
        }
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserDetailsDTO> getUserByEmail(@PathVariable("email") String email, @RequestHeader HttpHeaders headers) {
        try {
            if (!jwtValidator.validateToken(headers.getFirst("Authorization"))) {
                LOGGER.warn("Unauthorized attempt to fetch user by email.");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            UserDetailsDTO userDetails = userService.findUserByEmail(email);
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("User not found with email: {}", email);
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

    @PostMapping(value = "/token_generation/{userId}")
    public ResponseEntity<String> generateToken(@PathVariable("userId") UUID userId) {
        try {
            UserDetailsDTO userDetailsDTO = userService.findUserById(userId);
            String token = jwtValidator.generateToken(userId, userDetailsDTO.getRole());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Error generating token for user ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/u")
    public ResponseEntity<UUID> createUser(@Valid @RequestBody UserDetailsDTO userDetailsDTO) {
        UUID userId;
        try {
            userId = userService.addUser(userDetailsDTO);
        } catch (FieldFormatException | DuplicateResourceException e) {
            LOGGER.error("Error saving user!", e);
            return new ResponseEntity<>(null, e.getStatus());
        }

        try {
            JSONObject userObject = new JSONObject();
            userObject.put("userId", userId);
            userObject.put("devicesList", new JSONArray());

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(userObject.toString(), requestHeaders);

            String url = urlDeviceApi + "/user_device_mapping/d";
            restTemplate.postForEntity(url, request, String.class);
            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        } catch (RestClientException | JSONException e) {
            LOGGER.error("Error creating user-device mapping for user ID: {}", userId, e);
            userService.deleteUser(userId);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<UUID> addUser(@Valid @RequestBody UserDetailsDTO userDetailsDTO, @RequestHeader HttpHeaders headers) {
        if (!jwtValidator.validateToken(headers.getFirst("Authorization"))) {
            LOGGER.warn("Unauthorized attempt to add user.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UUID userId;
        try {
            userId = userService.addUser(userDetailsDTO);
        } catch (FieldFormatException | DuplicateResourceException e) {
            LOGGER.error("Error saving user!", e);
            return new ResponseEntity<>(null, e.getStatus());
        }

        try {
            JSONObject userObject = new JSONObject();
            userObject.put("userId", userId);
            userObject.put("devicesList", new JSONArray());

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.setBearerAuth(Objects.requireNonNull(headers.getFirst("Authorization")).substring(7));
            HttpEntity<String> request = new HttpEntity<>(userObject.toString(), requestHeaders);

            String url = urlDeviceApi + "/user_device_mapping";
            restTemplate.postForEntity(url, request, String.class);
            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        } catch (RestClientException | JSONException e) {
            LOGGER.error("Error creating user-device mapping for user ID: {}", userId, e);
            userService.deleteUser(userId);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") UUID userId, @RequestHeader HttpHeaders headers) {
        if (!jwtValidator.validateToken(headers.getFirst("Authorization"))) {
            LOGGER.warn("Unauthorized attempt to delete user.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("User not found for deletion: {}", userId);
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDetailsDTO> updateUserById(@Valid @RequestBody UserDetailsDTO userDetailsDTO, @PathVariable("id") UUID userId, @RequestHeader HttpHeaders headers) {
        if (!jwtValidator.validateToken(headers.getFirst("Authorization"))) {
            LOGGER.warn("Unauthorized attempt to update user.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            UserDetailsDTO updatedUser = userService.updateUser(userDetailsDTO, userId);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (ResourceNotFoundException | FieldFormatException | DuplicateResourceException e) {
            LOGGER.error("Error updating user ID: {}", userId);
            return new ResponseEntity<>(null, e.getStatus());
        }
    }
}
