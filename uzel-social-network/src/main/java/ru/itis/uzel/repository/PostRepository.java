package ru.itis.uzel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.uzel.entity.Post;
import ru.itis.uzel.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByAuthor(User author, Pageable pageable);
}
