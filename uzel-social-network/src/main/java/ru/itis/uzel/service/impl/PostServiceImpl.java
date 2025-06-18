package ru.itis.uzel.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.uzel.dto.PostForm;
import ru.itis.uzel.entity.FileInfo;
import ru.itis.uzel.entity.Post;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.mapper.PostMapper;
import ru.itis.uzel.repository.PostRepository;
import ru.itis.uzel.service.FileInfoService;
import ru.itis.uzel.service.PostService;
import ru.itis.uzel.util.ErrorsLogger;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FileInfoService fileInfoService;
    private final PostMapper postMapper;

    public void create(PostForm postForm, User user) {
        try {
            FileInfo fileInfo = null;
            MultipartFile file = postForm.getImage();
            if (file != null && !file.isEmpty()) {
                fileInfo = fileInfoService.saveFile(file);
            }

            Post post = postMapper.toEntity(postForm, user, fileInfo);

            postRepository.save(post);
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("Post", e);
        }
    }

    public Page<Post> getPostsByUser(User author, Pageable pageable) {
        try {
            return postRepository.findAllByAuthor(author, pageable);
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("Post", e);
        }
        return null;
    }

    public boolean togglePostLike(Long postId, User user) {
        try {
            Post post = postRepository.findById(postId).orElseThrow();
            boolean liked = post.getLikedBy().contains(user);
            if (liked) {
                post.getLikedBy().remove(user);
            } else {
                post.getLikedBy().add(user);
            }
            postRepository.save(post);
            return !liked;
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("Post", e);
        }
        return false;
    }
    @PostConstruct
    public void testLogger() {
        try {
            throw new IllegalStateException("IllegalStateException");
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("Post", e);
        }
    }
}
