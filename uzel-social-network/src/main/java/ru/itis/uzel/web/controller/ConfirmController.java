package ru.itis.uzel.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.uzel.service.ConfirmService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/confirm/{code}")
public class ConfirmController {

    private final ConfirmService confirmService;

    @GetMapping
    public String confirm(@PathVariable String code) {
        boolean result = confirmService.confirm(code);
        if (!result) {
            return "confirmation_failure";
        }
        confirmService.confirm(code);
        return "confirmation_success";
    }
}
