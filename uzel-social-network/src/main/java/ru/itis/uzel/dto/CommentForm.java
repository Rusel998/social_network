package ru.itis.uzel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentForm {
    @NotBlank
    @Size(max = 3000)
    private String content;
}
