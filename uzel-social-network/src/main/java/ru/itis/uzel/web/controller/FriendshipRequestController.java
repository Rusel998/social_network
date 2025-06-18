package ru.itis.uzel.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.uzel.entity.Friendship;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.security.details.UserDetailsImpl;
import ru.itis.uzel.service.FriendshipService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FriendshipRequestController {

    private final FriendshipService friendshipService;

    @GetMapping("/friend-requests")
    public String showFriendRequests(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     Model model) {
        User user = userDetails.getUser();
        List<Friendship> requests = friendshipService.getIncomingRequests(user);
        model.addAttribute("requests", requests);
        return "friends_requests";
    }
    @PostMapping("/friendships/{id}/accept")
    public String acceptFriendRequest(@PathVariable Long id,
                                      @AuthenticationPrincipal UserDetailsImpl currentUser) {
        User user = currentUser.getUser();
        friendshipService.acceptRequest(id, user);
        return "redirect:/friend-requests";
    }

    @PostMapping("/friendships/{id}/decline")
    public String declineFriendRequest(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetailsImpl currentUser) {
        User user = currentUser.getUser();
        friendshipService.declineRequest(id, user);
        return "redirect:/friend-requests";
    }

}
