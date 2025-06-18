package ru.itis.uzel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.uzel.dictionary.FriendshipStatus;
import ru.itis.uzel.entity.Friendship;
import ru.itis.uzel.entity.User;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsByUserAndFriend(User user, User friend);

    @Query("SELECT f FROM Friendship f WHERE " +
            "(f.user = :user OR f.friend = :user) AND f.status = :status")
    List<Friendship> findAllByParticipantAndStatus(@Param("user") User user,
                                                   @Param("status") FriendshipStatus status);

    List<Friendship> findAllByFriendAndStatus(User friend, FriendshipStatus status);
}