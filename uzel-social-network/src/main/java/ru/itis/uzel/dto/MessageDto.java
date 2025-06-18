package ru.itis.uzel.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long senderId;
    private Long recipientId;
    private String content;
    private LocalDateTime sentAt;
}