package ru.itis.uzel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.uzel.api.AvatarGeneratorService;
import ru.itis.uzel.dto.UserForm;
import ru.itis.uzel.entity.FileInfo;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.exception.UserAlreadyExistsException;
import ru.itis.uzel.mapper.UserMapper;
import ru.itis.uzel.repository.UserRepository;
import ru.itis.uzel.service.MailService;
import ru.itis.uzel.util.ErrorsLogger;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final AvatarGeneratorService avatarGeneratorService;

    public void createUser(UserForm userForm) {
        try {
            userRepository.findByEmail(userForm.getEmail())
                    .ifPresent(user -> {
                        throw new UserAlreadyExistsException("User already exists in DB");
                    });

            String encodedPassword = passwordEncoder.encode(userForm.getPassword());
            User user = userMapper.toEntity(userForm, encodedPassword);

            user.setConfirmCode(UUID.randomUUID().toString());

            FileInfo avatar = avatarGeneratorService.generateAvatarForUser(user.getFirstName() + UUID.randomUUID());
            user.setAvatar(avatar);

            userRepository.save(user);

            mailService.sendEmailForConfirm(user.getEmail(), user.getConfirmCode());
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("User", e);
        }
    }
}
