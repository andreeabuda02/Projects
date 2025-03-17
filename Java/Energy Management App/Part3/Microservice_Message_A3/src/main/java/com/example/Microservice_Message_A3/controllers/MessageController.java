package com.example.Microservice_Message_A3.controllers;

import com.example.Microservice_Message_A3.controllers.handlers.ResourceNotFoundException;
import com.example.Microservice_Message_A3.dtos.MessageDTO;
import com.example.Microservice_Message_A3.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class MessageController {

    private final MessageService messageHandler;

    @Autowired
    public MessageController(MessageService messageHandler) {
        this.messageHandler = messageHandler;
    }

    @MessageMapping("/send")
    @SendTo("/topic/newMessage")
    public MessageDTO sendMessage(@Valid @RequestBody MessageDTO messageData) {
        return messageHandler.registerMessage(messageData);
    }

    @GetMapping("/msg/{id}")
    public ResponseEntity<MessageDTO> retrieveMessageById(@PathVariable("id") UUID id) {
        try {
            MessageDTO retrievedMessage = messageHandler.fetchMessageById(id);
            return new ResponseEntity<>(retrievedMessage, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, e.getStatus());
        }
    }

    @GetMapping("/conv/{receiver}/{sender}")
    public ResponseEntity<List<MessageDTO>> retrieveConversation(@PathVariable("receiver") UUID receiverId, @PathVariable("sender") UUID senderId) {
        List<MessageDTO> conversation = messageHandler.getConversation(receiverId, senderId);
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }

    @GetMapping("/read/{receiver}/{sender}")
    public ResponseEntity<String> markMessagesRead(@PathVariable("receiver") UUID receiverId, @PathVariable("sender") UUID senderId) {
        try {
            String result = messageHandler.markMessagesRead(receiverId, senderId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "/interactions/{userId}")
    public ResponseEntity<List<UUID>> fetchInteractedUsers(@PathVariable("userId") UUID userId) {
        List<UUID> contactedUsers = messageHandler.retrieveInteractedUsers(userId);
        return new ResponseEntity<>(contactedUsers, HttpStatus.OK);
    }

    @GetMapping(value = "/unread/{userId}")
    public ResponseEntity<List<MessageDTO>> fetchUnreadMessages(@PathVariable("userId") UUID userId) {
        List<MessageDTO> unreadMessages = messageHandler.retrieveUnreadMessages(userId);
        return new ResponseEntity<>(unreadMessages, HttpStatus.OK);
    }

}
