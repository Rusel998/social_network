package ru.itis.uzel.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserForm {
    @NotBlank(message = "{userForm.firstName.notBlank}")
    private String firstName;

    @NotBlank(message = "{userForm.lastName.notBlank}")
    private String lastName;

    @Min(value = 1, message = "{userForm.age.min}")
    @Max(value = 120, message = "{userForm.age.max}")
    private Integer age;

    @Email(message = "{userForm.email.invalid}")
    @NotBlank(message = "{userForm.email.notBlank}")
    private String email;

    @NotBlank(message = "{userForm.password.notBlank}")
    @Size(min = 6, message = "{userForm.password.size}")
    private String password;

}
