package ru.itis.uzel.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.uzel.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c " +
            "WHERE c.post.id = :postId " +
            "ORDER BY SIZE(c.likedBy) DESC, c.createdAt DESC")
    List<Comment> findTop2ByPostOrderByLikesDesc(
            @Param("postId") Long postId,
            Pageable pageable);

    @Query("SELECT c FROM Comment c " +
            "WHERE c.post.id = :postId " +
            "ORDER BY c.createdAt ASC")
    List<Comment> findAllByPostOrderByCreatedAtAsc(@Param("postId") Long postId);
}
