package ru.itis.uzel.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.uzel.dto.UserDto;
import ru.itis.uzel.security.details.UserDetailsImpl;
import ru.itis.uzel.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/panel")
    public String adminPanel() {
        return "admin_panel";
    }

    @GetMapping("/users")
    public String userList(
            Model model,
            @AuthenticationPrincipal UserDetailsImpl currentUser
    ) {
        Long myId = currentUser.getUser().getId();
        List<UserDto> users = userService.findAllNonAdminExcept(myId);
        model.addAttribute("users", users);
        return "admin_users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Пользователь удалён");
        return "redirect:/admin/users";
    }
}