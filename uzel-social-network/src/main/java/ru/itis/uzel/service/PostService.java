package ru.itis.uzel.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.uzel.dto.PostForm;
import ru.itis.uzel.entity.Post;
import ru.itis.uzel.entity.User;

public interface PostService {

    void create(PostForm postForm, User user);

    Page<Post> getPostsByUser(User author, Pageable pageable);

    boolean togglePostLike(Long postId, User user);
}
