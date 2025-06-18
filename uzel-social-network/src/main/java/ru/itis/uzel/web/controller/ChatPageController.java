package ru.itis.uzel.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.uzel.converter.LocalDateTimeToStringConverter;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.exception.UserNotFoundException;
import ru.itis.uzel.repository.UserRepository;
import ru.itis.uzel.security.details.UserDetailsImpl;
import ru.itis.uzel.service.FriendshipService;
import ru.itis.uzel.service.MessageService;

@Controller
@RequiredArgsConstructor
public class ChatPageController {

    private final UserRepository userRepository;
    private final MessageService messageService;
    private final FriendshipService friendshipService;
    private final LocalDateTimeToStringConverter dateConverter;

    @GetMapping("/chat/{friendId}")
    public String chatPage(@PathVariable Long friendId,
                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                           Model model) {

        User currentUser = userDetailsImpl.getUser();
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + friendId));

        if (!friendshipService.existsFriendship(currentUser, friend)) {
            return "/error/404";
        }

        messageService.markMessagesAsRead(currentUser.getId(), friendId);

        model.addAttribute("dateConverter", dateConverter);
        model.addAttribute("user", currentUser);
        model.addAttribute("friend", friend);
        model.addAttribute("messages", messageService.getChatHistory(currentUser.getId(), friendId));

        return "chat";
    }
}