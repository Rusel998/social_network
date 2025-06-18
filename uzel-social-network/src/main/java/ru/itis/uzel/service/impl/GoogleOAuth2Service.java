package ru.itis.uzel.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.itis.uzel.api.AvatarGeneratorService;
import ru.itis.uzel.entity.FileInfo;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.mapper.UserMapper;
import ru.itis.uzel.repository.UserRepository;
import ru.itis.uzel.service.OAuth2Service;
import ru.itis.uzel.util.ErrorsLogger;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleOAuth2Service implements OAuth2Service {

    private final UserRepository userRepository;
    private final AvatarGeneratorService avatarGeneratorService;
    private final UserMapper userMapper;
    private static final String PASSWORD = "OAUTH2_USER" + UUID.randomUUID();

    @Override
    public User saveOauth2User(OAuth2AuthenticationToken token) {
        try {
            OAuth2User oAuth2User = token.getPrincipal();
            String email = oAuth2User.getAttribute("email");
            String firstName = oAuth2User.getAttribute("given_name");
            String lastName = oAuth2User.getAttribute("family_name");

            if (lastName == null) {
                lastName = "_" + UUID.randomUUID();
            }

            Optional<User> existingUser = userRepository.findByEmail(email);

            User finalUser = userMapper.mapToUserFromOAuth2(email, firstName, lastName, PASSWORD);
            existingUser.ifPresent(user -> finalUser.setId(user.getId()));
            FileInfo fileInfo = avatarGeneratorService.generateAvatarForUser(finalUser.getFirstName() + UUID.randomUUID());
            finalUser.setAvatar(fileInfo);
            return userRepository.save(finalUser);
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("User", e);
            throw new InternalAuthenticationServiceException("OAuth2 login failed", e);
        }
    }
}