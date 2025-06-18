package ru.itis.uzel.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Data
@Accessors(chain = true)
public class ProfileForm {
    @NotBlank(message = "{profileForm.firstName.notBlank}")
    private String firstName;

    @NotBlank(message = "{profileForm.lastName.notBlank}")
    private String lastName;

    @Min(value = 1, message = "{profileForm.age.min}")
    @Max(value = 120, message = "{profileForm.age.max}")
    private Integer age;

    @Size(max = 1000, message = "{profileForm.bio.size}")
    private String bio;

    private MultipartFile avatarFile;
}