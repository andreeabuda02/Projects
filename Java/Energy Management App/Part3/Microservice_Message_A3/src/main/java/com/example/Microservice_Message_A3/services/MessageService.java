package com.example.Microservice_Message_A3.services;

import com.example.Microservice_Message_A3.controllers.handlers.ResourceNotFoundException;
import com.example.Microservice_Message_A3.dtos.MessageDTO;
import com.example.Microservice_Message_A3.dtos.builders.MessageBuilder;
import com.example.Microservice_Message_A3.entities.Message;
import com.example.Microservice_Message_A3.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
    private final MessageRepository messageRepo;

    @Autowired
    public MessageService(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Transactional
    public MessageDTO registerMessage(MessageDTO messageData) {
        Message messageEntity = MessageBuilder.convertToMessage(messageData);
        messageEntity.setMessageRead(false);
        messageEntity = messageRepo.save(messageEntity);
        LOGGER.debug("New message added with ID: {}", messageEntity.getMessageId());
        return MessageBuilder.convertToMessageDTO(messageEntity);
    }

    @Transactional
    public MessageDTO fetchMessageById(UUID messageId) throws ResourceNotFoundException {
        Optional<Message> messageRecord = messageRepo.findById(messageId);
        if (messageRecord.isEmpty()) {
            LOGGER.error("Message with ID {} not located!", messageId);
            throw new ResourceNotFoundException("Message with ID: " + messageId);
        }
        return MessageBuilder.convertToMessageDTO(messageRecord.get());
    }

    @Transactional
    public List<MessageDTO> getConversation(UUID participantA, UUID participantB) {
        List<Message> messages = messageRepo.findAll();
        List<MessageDTO> conversationLog = new ArrayList<>();

        messages.stream()
                .filter(msg -> (msg.getReceiverId().equals(participantA) && msg.getSenderId().equals(participantB)) ||
                        (msg.getReceiverId().equals(participantB) && msg.getSenderId().equals(participantA)))
                .sorted(Comparator.comparing(Message::getTimestamp).reversed())
                .forEach(msg -> conversationLog.add(MessageBuilder.convertToMessageDTO(msg)));

        LOGGER.info("Retrieved {} messages between {} and {}", conversationLog.size(), participantA, participantB);
        return conversationLog;
    }

    @Transactional
    public String markMessagesRead(UUID receiverId, UUID senderId) throws ResourceNotFoundException {
        List<Message> receiverMessages = messageRepo.findAllByReceiverId(receiverId);
        List<Message> senderMessages = messageRepo.findAllBySenderId(senderId);

        if (receiverMessages.isEmpty() || senderMessages.isEmpty()) {
            LOGGER.error("No messages found between {} and {}", receiverId, senderId);
            throw new ResourceNotFoundException("Messages not found for these users.");
        }

        receiverMessages.stream()
                .filter(msg -> !msg.isMessageRead())
                .forEach(msg -> {
                    msg.setMessageRead(true);
                    messageRepo.save(msg);
                });

        LOGGER.info("Messages marked as read between {} and {}", receiverId, senderId);
        return "All messages marked as read.";
    }

    @Transactional
    public List<UUID> retrieveInteractedUsers(UUID userId) {
        List<Message> messageHistory = messageRepo.findAll();
        List<UUID> contactedUsers = new ArrayList<>();

        messageHistory.sort(Comparator.comparing(Message::getTimestamp).reversed());

        for (Message msg : messageHistory) {
            if (msg.getReceiverId().equals(userId) || msg.getSenderId().equals(userId)) {
                UUID correspondent = msg.getReceiverId().equals(userId) ? msg.getSenderId() : msg.getReceiverId();

                if (!contactedUsers.contains(correspondent)) {
                    contactedUsers.add(correspondent);
                }
            }
        }
        return contactedUsers;
    }

    @Transactional
    public List<MessageDTO> retrieveUnreadMessages(UUID userId) {
        List<Message> unreadMsgs = messageRepo.findAllByReceiverId(userId);
        List<MessageDTO> unreadMessagesDTO = new ArrayList<>();

        unreadMsgs.stream()
                .filter(msg -> !msg.isMessageRead())
                .sorted(Comparator.comparing(Message::getTimestamp).reversed())
                .forEach(msg -> unreadMessagesDTO.add(MessageBuilder.convertToMessageDTO(msg)));

        LOGGER.info("Retrieved {} unread messages for user {}", unreadMessagesDTO.size(), userId);
        return unreadMessagesDTO;
    }

}
