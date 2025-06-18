package ru.itis.uzel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostForm {

    @NotBlank(message = "{postForm.content.notBlank}")
    @Size(max = 5000, message = "{postForm.content.size}")
    private String content;

    private MultipartFile image;

    @Size(max = 255, message = "{postForm.location.size}")
    private String location;
}