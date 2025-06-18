package ru.itis.uzel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.uzel.dictionary.UserStatus;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.repository.UserRepository;
import ru.itis.uzel.service.ConfirmService;
import ru.itis.uzel.util.ErrorsLogger;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ConfirmServiceImpl implements ConfirmService {

    private final UserRepository userRepository;

    @Override
    public boolean confirm(String code) {
        try {
            Optional<User> userOpt = userRepository.findByConfirmCode(code);
            if (userOpt.isEmpty()) {
                return false;
            }
            User user = userOpt.get();
            user.setStatus(UserStatus.ACTIVE);
            user.setConfirmCode(null);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("User", e);
        }
        return false;
    }
}
