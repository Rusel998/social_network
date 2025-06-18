package ru.itis.uzel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageForm {

    @NotNull
    private Long recipientId;

    @NotBlank
    @Size(max = 5000)
    private String content;
}