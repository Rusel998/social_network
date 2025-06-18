package ru.itis.uzel.service;

import ru.itis.uzel.dto.CommentDto;
import ru.itis.uzel.dto.CommentForm;
import ru.itis.uzel.entity.User;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface CommentService {
    CommentDto addComment(Long postId, CommentForm form, User user);
    List<CommentDto> getCommentsForPost(Long postId, User currentUser);
    boolean toggleCommentLike(Long commentId, User user);
    void deleteComment(Long commentId, User user) throws AccessDeniedException;
    List<CommentDto> getCommentsForPost(Long postId, User currentUser, boolean top);
}