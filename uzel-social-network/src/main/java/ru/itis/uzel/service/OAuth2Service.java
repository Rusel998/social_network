package ru.itis.uzel.service;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import ru.itis.uzel.entity.User;

public interface OAuth2Service {

    User saveOauth2User(OAuth2AuthenticationToken authentication);
}
