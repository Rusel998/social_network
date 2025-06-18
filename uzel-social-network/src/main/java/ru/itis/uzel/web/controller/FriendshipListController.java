package ru.itis.uzel.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.uzel.entity.Friendship;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.security.details.UserDetailsImpl;
import ru.itis.uzel.service.FriendshipService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FriendshipListController {

    private final FriendshipService friendshipService;

    @GetMapping("/friends")
    public String showFriends(@ModelAttribute("user") User currentUser, Model model) {
        List<Friendship> friends = friendshipService.getAcceptedFriendships(currentUser);
        model.addAttribute("friends", friends);
        return "friends_list";
    }
    @PostMapping("/friendships/{id}/note")
    public String updateNote(@PathVariable Long id,
                             @RequestParam String relationshipNote,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        friendshipService.updateNote(id, relationshipNote, user);
        return "redirect:/friends";
    }


}
