package ru.itis.uzel.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.security.details.UserDetailsImpl;
import ru.itis.uzel.service.FriendshipService;

import java.util.Locale;


@ControllerAdvice
@RequiredArgsConstructor
public class GlobalAdvice {

    private final FriendshipService friendshipService;

    @ModelAttribute("user")
    public User addUserToModel() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetailsImpl userDetails) {
                return userDetails.getUser();
            }
        }
        return null;
    }

    @ModelAttribute("lang")
    public String getLanguage(Locale locale) {
        return locale.getLanguage();
    }

    @ModelAttribute("friendRequestsCount")
    public int getFriendRequestsCount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetailsImpl userDetails) {
                return friendshipService.getIncomingRequests(userDetails.getUser()).size();
            }
        }
        return 0;
    }
}
