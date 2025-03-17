package com.example.Microservice_Message_A3.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "message_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID messageId;
    @Column(name = "id_sender")
    private UUID senderId;
    @Column(name = "id_receiver")
    private UUID receiverId;
    @Column(name = "details_message")
    private String messageDetails;
    @Column(name = "timestamp")
    private Timestamp timestamp;
    @Column(name = "read_message")
    private boolean messageRead;
}
