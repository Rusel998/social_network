package ru.itis.uzel.service;

import ru.itis.uzel.dto.ProfileForm;
import ru.itis.uzel.dto.UserDto;
import ru.itis.uzel.entity.User;

import java.util.List;

public interface UserService {

    void updateProfile(User user, ProfileForm form);

    ProfileForm toProfileForm(User user);

    User findUserByEmail(String email);

    User findUserById(Long id);

    List<UserDto> searchUsers(String query);

    void deleteById(Long id);

    List<UserDto> findAllNonAdminExcept(Long currentUserId);
}
