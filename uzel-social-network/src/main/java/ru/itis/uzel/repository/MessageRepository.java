package ru.itis.uzel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.uzel.entity.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("""
        SELECT m FROM Message m
        WHERE (m.sender.id = :userId AND m.recipient.id = :friendId)
           OR (m.sender.id = :friendId AND m.recipient.id = :userId)
        ORDER BY m.sentAt
    """)
    List<Message> findChatHistory(@Param("userId") Long userId, @Param("friendId") Long friendId);

    @Modifying
    @Query("""
    UPDATE Message m
    SET m.isRead = true,
        m.updatedAt = CURRENT_TIMESTAMP
    WHERE m.recipient.id = :userId
      AND m.sender.id = :friendId
      AND m.isRead = false
""")
    void markMessagesAsRead(@Param("userId") Long userId, @Param("friendId") Long friendId);

}
