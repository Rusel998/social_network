package ru.itis.uzel.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.uzel.dto.CommentDto;
import ru.itis.uzel.dto.CommentForm;
import ru.itis.uzel.entity.Comment;
import ru.itis.uzel.entity.Post;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.exception.PostNotFoundException;
import ru.itis.uzel.mapper.CommentMapper;
import ru.itis.uzel.repository.CommentRepository;
import ru.itis.uzel.repository.PostRepository;
import ru.itis.uzel.service.CommentService;
import ru.itis.uzel.util.ErrorsLogger;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto addComment(Long postId, CommentForm form, User user) {
        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException("Post not found"));
            Comment comment = commentMapper.toEntity(form, user, post);
            commentRepository.save(comment);
            return commentMapper.toDto(comment, user);
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("Comment", e);
        }
        return null;
    }

    @Override
    public List<CommentDto> getCommentsForPost(Long postId, User currentUser, boolean top) {
        try {
            if (top) {
                return commentRepository.findTop2ByPostOrderByLikesDesc(
                                postId,
                                org.springframework.data.domain.PageRequest.of(0, 2)
                        ).stream()
                        .map(comment -> commentMapper.toDto(comment, currentUser))
                        .toList();
            } else {
                return getCommentsForPost(postId, currentUser);
            }
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("Comment", e);
        }
        return null;
    }

    @Override
    public List<CommentDto> getCommentsForPost(Long postId, User currentUser) {
        try {
            return commentRepository.findAllByPostOrderByCreatedAtAsc(postId).stream()
                    .map(comment -> commentMapper.toDto(comment, currentUser))
                    .toList();
        } catch (Exception e){
            ErrorsLogger.writeErrorToFile("Comment", e);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean toggleCommentLike(Long commentId, User user) {
        try {
            Comment comment = commentRepository.findById(commentId).orElseThrow();
            boolean liked = comment.getLikedBy().contains(user);
            if (liked) {
                comment.getLikedBy().remove(user);
            } else {
                comment.getLikedBy().add(user);
            }
            commentRepository.save(comment);
            return !liked;
        } catch (Exception e){
            ErrorsLogger.writeErrorToFile("Comment", e);
        }
        return false;
    }

    @Override
    public void deleteComment(Long commentId, User user) throws AccessDeniedException {
        try {
            Comment comment = commentRepository.findById(commentId).orElseThrow();

            boolean isPostOwner = comment.getPost().getAuthor().getId().equals(user.getId());
            if (!isPostOwner) {
                throw new AccessDeniedException("Только автор поста может удалять комментарии");
            }

            commentRepository.delete(comment);
        } catch (Exception e){
            ErrorsLogger.writeErrorToFile("Comment", e);
        }
    }
}
