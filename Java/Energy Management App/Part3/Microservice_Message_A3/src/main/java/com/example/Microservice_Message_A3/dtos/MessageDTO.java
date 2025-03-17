package com.example.Microservice_Message_A3.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Timestamp;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MessageDTO extends RepresentationModel<MessageDTO> {
    private UUID messageId;
    private UUID senderId;
    private UUID receiverId;
    private String messageDetails;
    private Timestamp timestamp;
    private boolean messageRead;
}
