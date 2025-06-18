package ru.itis.uzel.web.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.uzel.dto.UserForm;
import ru.itis.uzel.service.impl.RegistrationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping
    public String signUp(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "registration";
    }

    @PostMapping
    public String signUp(@Valid UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        registrationService.createUser(userForm);
        return "redirect:/register";
    }

}
