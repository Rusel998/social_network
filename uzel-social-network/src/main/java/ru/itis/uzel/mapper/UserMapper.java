package ru.itis.uzel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.uzel.dto.UserDto;
import ru.itis.uzel.dto.UserForm;
import ru.itis.uzel.entity.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "confirmCode", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "status", constant = "NEED_CONFIRMATION")
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "bio", ignore = true)
    User toEntity(UserForm userForm, String encodedPassword);


    @Mapping(target = "avatarUrl", source = "avatar.url")
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "status", constant = "ACTIVE")
    User mapToUserFromOAuth2(String email, String firstName, String lastName, String password);
}
