package ru.itis.uzel.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.uzel.dto.ProfileForm;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/profile/edit")
@RequiredArgsConstructor
public class ProfileEditController {

    private final UserServiceImpl userService;


    @GetMapping
    public String editProfile(@ModelAttribute("user") User currentUser, Model model) {
        ProfileForm profileForm = userService.toProfileForm(currentUser);
        model.addAttribute("profileForm", profileForm);
        return "profile_edit";
    }

    @PostMapping
    public String updateProfile(@ModelAttribute("user") User user,
                                @Valid @ModelAttribute("profileForm") ProfileForm form,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile_edit";
        }
        userService.updateProfile(user, form);
        return "redirect:/profile/" + user.getId();
    }
}

