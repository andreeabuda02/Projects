package com.example.Microservice_Message_A3.dtos.builders;

import com.example.Microservice_Message_A3.dtos.MessageDTO;
import com.example.Microservice_Message_A3.entities.Message;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MessageBuilder {
    public static MessageDTO convertToMessageDTO(Message message){
        return MessageDTO.builder()
                .messageId(message.getMessageId())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .messageDetails(message.getMessageDetails())
                .timestamp(message.getTimestamp())
                .messageRead(message.isMessageRead())
                .build();
    }


    public static Message convertToMessage(MessageDTO messageDTO){
        return Message.builder()
                .messageId(messageDTO.getMessageId())
                .senderId(messageDTO.getSenderId())
                .receiverId(messageDTO.getReceiverId())
                .messageDetails(messageDTO.getMessageDetails())
                .timestamp(messageDTO.getTimestamp())
                .messageRead(messageDTO.isMessageRead())
                .build();
    }

}
