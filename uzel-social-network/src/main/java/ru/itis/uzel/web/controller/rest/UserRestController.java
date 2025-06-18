package ru.itis.uzel.web.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.uzel.dto.UserDto;
import ru.itis.uzel.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/api/users/search")
    public List<UserDto> searchUsers(@RequestParam String query) {
        return userService.searchUsers(query);
    }
}