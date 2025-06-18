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
import ru.itis.uzel.dto.PostForm;
import ru.itis.uzel.entity.Post;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.service.impl.PostServiceImpl;

@Controller
@RequestMapping("/posts/create")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    @GetMapping
    public String create(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "post_create";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user,
                         @ModelAttribute("postForm") @Valid PostForm form,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post_create";
        }
        postService.create(form, user);
        return "redirect:/profile/" + user.getId();
    }
}
