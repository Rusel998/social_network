package ru.itis.uzel.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.uzel.converter.LocalDateTimeToStringConverter;
import ru.itis.uzel.entity.Post;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.security.details.UserDetailsImpl;
import ru.itis.uzel.service.FriendshipService;
import ru.itis.uzel.service.UserService;
import ru.itis.uzel.service.impl.PostServiceImpl;

import java.time.LocalDateTime;
import java.util.function.Function;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final PostServiceImpl postService;
    private final LocalDateTimeToStringConverter localDateTimeToStringConverter;
    private final UserService userService;
    private final FriendshipService friendshipService;

    @GetMapping("/{id}")
    public String viewProfile(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetailsImpl currentUser,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              Model model) {
        User viewedUser = userService.findUserById(id);
        User current = userService.findUserById(currentUser.getUser().getId());
        Page<Post> postsPage = postService.getPostsByUser(viewedUser, PageRequest.of(page, size));

        boolean alreadyRequestedOrFriends = friendshipService.existsFriendship(current, viewedUser);
        boolean areFriends = friendshipService.areFriends(current, viewedUser);

        model.addAttribute("areFriends", areFriends);
        model.addAttribute("isFriendOrPending", alreadyRequestedOrFriends);
        model.addAttribute("viewedUser", viewedUser);
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("dateConverter", localDateTimeToStringConverter);
        return "profile";
    }

}

