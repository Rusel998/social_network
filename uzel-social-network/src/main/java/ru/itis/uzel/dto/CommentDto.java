package ru.itis.uzel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private Long authorId;
    private String text;
    private String authorName;
    @JsonFormat(pattern = "dd MMM yyyy HH:mm", locale = "ru")
    private LocalDateTime createdAt;
    private int likeCount;
    private boolean likedByCurrentUser;
    private boolean deletable;
}
