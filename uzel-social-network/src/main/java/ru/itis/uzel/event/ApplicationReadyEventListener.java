package ru.itis.uzel.event;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.uzel.api.AvatarGeneratorService;
import ru.itis.uzel.dictionary.UserRole;
import ru.itis.uzel.dictionary.UserStatus;
import ru.itis.uzel.entity.FileInfo;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.repository.UserRepository;

import java.util.UUID;


@Component
@Profile("dev")
@RequiredArgsConstructor
public class ApplicationReadyEventListener {

    private static final String ADMIN_EMAIL = "rmilos569@gmail.com";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AvatarGeneratorService avatarGeneratorService;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {

            User user = new User()
                    .setEmail(ADMIN_EMAIL)
                    .setPassword(passwordEncoder.encode("123"))
                    .setFirstName("АДМИН")
                    .setLastName("АДМИНСКИЙ")
                    .setAge(30)
                    .setRole(UserRole.ADMIN)
                    .setStatus(UserStatus.ACTIVE);
            FileInfo avatar = avatarGeneratorService.generateAvatarForUser(user.getFirstName() + UUID.randomUUID());
            user.setAvatar(avatar);
            userRepository.save(user);

//            User user1 = new User()
//                    .setEmail("rus.urunov.00@mail.ru")
//                    .setPassword(passwordEncoder.encode("123"))
//                    .setAge(22)
//                    .setFirstName("РУСЛАН")
//                    .setLastName("УРУНОВ")
//                    .setRole(UserRole.USER)
//                    .setStatus(UserStatus.ACTIVE);
//            FileInfo avatar1 = avatarGeneratorService.generateAvatarForUser(user.getFirstName() + UUID.randomUUID());
//            user1.setAvatar(avatar1);
//            userRepository.save(user1);

        }
    }

}

