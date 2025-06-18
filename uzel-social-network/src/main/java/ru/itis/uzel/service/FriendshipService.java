package ru.itis.uzel.service;

import ru.itis.uzel.dto.FriendshipForm;
import ru.itis.uzel.entity.Friendship;
import ru.itis.uzel.entity.User;

import java.util.List;


public interface FriendshipService {

    void createFriendRequest(FriendshipForm form, User sender, User receiver);

    boolean existsFriendship(User a, User b);

    List<Friendship> getAcceptedFriendships(User user);

    void updateNote(Long id, String note, User user);

    List<Friendship> getIncomingRequests(User currentUser);

    void declineRequest(Long id, User currentUser);

    void acceptRequest(Long id, User currentUser);

    boolean areFriends(User user1, User user2);

}
