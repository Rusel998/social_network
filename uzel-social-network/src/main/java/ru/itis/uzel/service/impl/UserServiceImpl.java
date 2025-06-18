package ru.itis.uzel.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.uzel.dictionary.UserRole;
import ru.itis.uzel.dto.ProfileForm;
import ru.itis.uzel.dto.UserDto;
import ru.itis.uzel.entity.FileInfo;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.exception.UserNotFoundException;
import ru.itis.uzel.mapper.UserMapper;
import ru.itis.uzel.repository.UserRepository;
import ru.itis.uzel.service.FileInfoService;
import ru.itis.uzel.service.UserService;
import ru.itis.uzel.util.ErrorsLogger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FileInfoService fileInfoService;
    private final UserMapper userMapper;

    @Override
    public void updateProfile(User user, ProfileForm form) {
        try {
            user.setFirstName(form.getFirstName())
                    .setLastName(form.getLastName())
                    .setAge(form.getAge())
                    .setBio(form.getBio());

            MultipartFile file = form.getAvatarFile();
            if (file != null && !file.isEmpty()) {
                FileInfo fileInfo = fileInfoService.saveFile(file);
                user.setAvatar(fileInfo);
            }

            userRepository.save(user);
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("User", e);
        }
    }

    @Override
    public ProfileForm toProfileForm(User user) {
        try {
            ProfileForm form = new ProfileForm();
            form.setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setAge(user.getAge())
                    .setBio(user.getBio());
            return form;
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("User", e);
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("User", e);
        }
        return null;
    }

    @Override
    public User findUserById(Long id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("User", e);
        }
        return null;
    }

    @Override
    public List<UserDto> searchUsers(String query) {
        try {
            return userRepository.searchByNameOrSurname(query)
                    .stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("User", e);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            userRepository.delete(user);
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("User", e);
        }
    }

    @Override
    public List<UserDto> findAllNonAdminExcept(Long currentUserId) {
        return userRepository
                .findAllByRoleNot(UserRole.ADMIN)
                .stream()
                .filter(u -> !u.getId().equals(currentUserId))
                .map(u -> UserDto.builder()
                        .id(u.getId())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .avatarUrl(u.getAvatar().getUrl())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void testLogger() {
        try {
            throw new IllegalStateException("IllegalStateException");
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("User", e);
        }
    }
}
