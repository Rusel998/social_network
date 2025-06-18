package ru.itis.uzel.web.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.uzel.dto.CommentDto;
import ru.itis.uzel.dto.CommentForm;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.service.CommentService;
import ru.itis.uzel.service.PostService;
import ru.itis.uzel.service.UserService;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostInteractionRestController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    private User getCurrentUser(Principal principal) {
        return userService.findUserByEmail(principal.getName());
    }

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<Map<String, Object>> togglePostLike(@PathVariable Long id, Principal principal) {
        boolean liked = postService.togglePostLike(id, getCurrentUser(principal));
        return ResponseEntity.ok(Map.of("liked", liked));
    }

    @PostMapping("/comments/{id}/like")
    public ResponseEntity<Map<String, Object>> toggleCommentLike(@PathVariable Long id, Principal principal) {
        boolean liked = commentService.toggleCommentLike(id, getCurrentUser(principal));
        return ResponseEntity.ok(Map.of("liked", liked));
    }

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long id,
                                                 @Valid @RequestBody CommentForm form,
                                                 Principal principal) {
        return ResponseEntity.ok(commentService.addComment(id, form, getCurrentUser(principal)));
    }

    @GetMapping("/posts/{id}/comments")
    public List<CommentDto> getComments(
            Principal principal,
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean top
    ) {
        User currentUser = getCurrentUser(principal);
        return commentService.getCommentsForPost(id, currentUser, top);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, Principal principal) throws AccessDeniedException {
        commentService.deleteComment(id, getCurrentUser(principal));
        return ResponseEntity.ok().build();
    }
}