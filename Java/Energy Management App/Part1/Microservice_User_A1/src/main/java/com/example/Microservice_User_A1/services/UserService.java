package com.example.Microservice_User_A1.services;

import com.example.Microservice_User_A1.controllers.handlers.excetions.model.DuplicateResourceException;
import com.example.Microservice_User_A1.controllers.handlers.excetions.model.FieldFormatException;
import com.example.Microservice_User_A1.controllers.handlers.excetions.model.ResourceNotFoundException;
import com.example.Microservice_User_A1.dtos.UserDTO;
import com.example.Microservice_User_A1.dtos.UserDetailsDTO;
import com.example.Microservice_User_A1.dtos.builders.UserBuilder;
import com.example.Microservice_User_A1.dtos.validators.EmailValidator;
import com.example.Microservice_User_A1.entities.User;
import com.example.Microservice_User_A1.repositories.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<UserDTO> findAllUsers(){
        LOGGER.info("Fetching all users from the database.");
        List<User> userList;
        userList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User u: userList) {
            userDTOList.add(UserBuilder.convertToUserDTO(u));
        }
        LOGGER.debug("Total users retrieved: {}", userDTOList.size());
        return userDTOList;
    }

    @Transactional
    public UserDetailsDTO findUserById(UUID id) throws ResourceNotFoundException {
        LOGGER.info("Fetching user by ID: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            LOGGER.error("User with ID {} not found.", id);
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        LOGGER.debug("User with ID {} retrieved successfully.", id);
        return UserBuilder.convertToUserDetailsDTO(user.get());
    }

    @Transactional
    public UserDetailsDTO findUserByEmail(String email) throws ResourceNotFoundException {
        LOGGER.info("Fetching user by email: {}", email);
        Optional<User> optionalUser = userRepository.findUsersByEmail(email);
        if (optionalUser.isEmpty()) {
            LOGGER.error("User with email {} not found.", email);
            throw new ResourceNotFoundException("User with email " + email + " not found.");
        }
        LOGGER.debug("User with email {} retrieved successfully.", email);
        return UserBuilder.convertToUserDetailsDTO(optionalUser.get());
    }

    @Transactional
    public UserDetailsDTO findUserByEmailAndPassword(String email, String password) throws ResourceNotFoundException {
        LOGGER.info("Fetching user by email and password.");
        Optional<User> optionalUser = userRepository.findUserByEmailAndPassword(email, password);
        if (optionalUser.isEmpty()) {
            LOGGER.error("User with email {} and password {} not found.", email, password);
            throw new ResourceNotFoundException("User with email " + email + " and specified password not found.");
        }
        LOGGER.debug("User with email {} retrieved successfully.", email);
        return UserBuilder.convertToUserDetailsDTO(optionalUser.get());
    }

    @Transactional
    public UUID addUser(UserDetailsDTO userDetailsDTO) throws FieldFormatException, DuplicateResourceException {
        LOGGER.info("Adding a new user with email: {}", userDetailsDTO.getEmail());
        if (userDetailsDTO.getPassword().length() < 8) {
            LOGGER.error("Password of the user is shorter than 8 characters!");
            throw new FieldFormatException("Password must be at least 8 characters for user with email: " + userDetailsDTO.getEmail());
        }
        if (!userDetailsDTO.getRole().equalsIgnoreCase("admin") && !userDetailsDTO.getRole().equalsIgnoreCase("client")) {
            LOGGER.error("Users can be clients or admins!");
            throw new FieldFormatException("Role must be either 'admin' or 'client' for user with email: " + userDetailsDTO.getEmail());
        }
        if (!EmailValidator.isValid(userDetailsDTO.getEmail())) {
            LOGGER.error("Email of the user doesn't have a valid format!");
            throw new FieldFormatException("Invalid email format for user with email: " + userDetailsDTO.getEmail());
        }
        if (userRepository.findUsersByEmail(userDetailsDTO.getEmail()).isPresent()) {
            LOGGER.error("User with email {} already exists.", userDetailsDTO.getEmail());
            throw new DuplicateResourceException("User with email " + userDetailsDTO.getEmail() + " already exists.");
        }
        userDetailsDTO.setRole(userDetailsDTO.getRole().toLowerCase());

        User user = UserBuilder.convertToUser(userDetailsDTO);
        user = userRepository.save(user);

        LOGGER.debug("User with ID {} has been added to the database.", user.getId_u());
        return user.getId_u();
    }


    @Transactional
    public String deleteUser(UUID id) throws ResourceNotFoundException {
        LOGGER.info("Deleting user by ID: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            LOGGER.error("User with ID {} not found for deletion.", id);
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        userRepository.deleteById(id);
        LOGGER.debug("User with ID {} has been deleted from the database.", id);
        return "The user with ID " + id + " has been deleted!";
    }

    @Transactional
    public UserDetailsDTO updateUser(UserDetailsDTO userDetailsDTO, UUID id) throws ResourceNotFoundException, FieldFormatException, DuplicateResourceException {
        LOGGER.info("Initiating update for user with ID: {}", id);

        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isEmpty()) {
            LOGGER.error("Update failed - user with ID {} does not exist.", id);
            throw new ResourceNotFoundException("User with ID " + id + " was not located.");
        }

        if (!EmailValidator.isValid(userDetailsDTO.getEmail())) {
            LOGGER.error("Provided email for user update has an invalid format: {}", userDetailsDTO.getEmail());
            throw new FieldFormatException("Invalid email format for user with email: " + userDetailsDTO.getEmail());
        }

        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            if (!user.getId_u().equals(id) && user.getEmail().equals(userDetailsDTO.getEmail())) {
                LOGGER.error("Email conflict: another user already registered with this email: {}", userDetailsDTO.getEmail());
                throw new DuplicateResourceException("Another user already exists with email: " + userDetailsDTO.getEmail());
            }
        }

        User updatedUser = existingUserOpt.map(user -> {
            user.setName(userDetailsDTO.getName());
            user.setRole(userDetailsDTO.getRole().toLowerCase()); // Standardizing role format
            user.setEmail(userDetailsDTO.getEmail());
            user.setPassword(userDetailsDTO.getPassword());
            return userRepository.save(user);
        }).get();

        LOGGER.debug("User with ID {} has been successfully updated in the system.", id);
        return UserBuilder.convertToUserDetailsDTO(updatedUser);
    }

}
