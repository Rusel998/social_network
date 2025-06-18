package ru.itis.uzel.web.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.uzel.dto.FriendshipForm;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.service.FriendshipService;
import ru.itis.uzel.service.UserService;

@RestController
@RequiredArgsConstructor
public class FriendshipRequestRestController {

    private final FriendshipService friendshipService;
    private final UserService userService;

    @PostMapping("/friendships")
    public ResponseEntity<?> sendFriendRequest(@RequestBody FriendshipForm form,
                                               @ModelAttribute("user") User sender) {
        User receiver = userService.findUserById(form.getFriendId());
        if (sender.equals(receiver)) {
            return ResponseEntity.badRequest().body("Нельзя добавить самого себя");
        }
        if (friendshipService.existsFriendship(sender, receiver)) {
            return ResponseEntity.badRequest().body("Уже в друзьях или заявка отправлена");
        }
        friendshipService.createFriendRequest(form, sender, receiver);
        return ResponseEntity.ok().build();
    }
}
