package ru.itis.uzel.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FriendshipForm {

    @NotNull
    private Long friendId;

    @Size(max = 255)
    private String relationshipNote;
}