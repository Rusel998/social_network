package ru.itis.uzel.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.itis.uzel.dictionary.FriendshipStatus;
import ru.itis.uzel.dto.FriendshipForm;
import ru.itis.uzel.entity.Friendship;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.exception.FriendshipNotFoundException;
import ru.itis.uzel.mapper.FriendshipMapper;
import ru.itis.uzel.repository.FriendshipRepository;
import ru.itis.uzel.service.FriendshipService;
import ru.itis.uzel.util.ErrorsLogger;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;

    @Override
    public void createFriendRequest(FriendshipForm form, User sender, User receiver) {
        try {
            Friendship friendship = friendshipMapper.toEntity(form, sender, receiver);
            friendshipRepository.save(friendship);
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("Friendship", e);
        }
    }

    @Override
    public boolean existsFriendship(User a, User b) {
        try {
            return friendshipRepository.existsByUserAndFriend(a, b)
                    || friendshipRepository.existsByUserAndFriend(b, a);
        }catch (Exception e){
            ErrorsLogger.writeErrorToFile("Friendship", e);
        }
        return false;
    }

    @Override
    public List<Friendship> getAcceptedFriendships(User user) {
        try {
            return friendshipRepository.findAllByParticipantAndStatus(user, FriendshipStatus.ACCEPTED);
        } catch (Exception e){
            ErrorsLogger.writeErrorToFile("Friendship", e);
        }
        return null;
    }

    @Override
    public void updateNote(Long id, String note, User currentUser) {
        try {
            Friendship friendship = friendshipRepository.findById(id)
                    .filter(f ->
                            f.getUser().equals(currentUser)
                                    || f.getFriend().equals(currentUser)
                    )
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
                            "Вы не можете изменить заметку к этой дружбе"));
            friendship.setRelationshipNote(note);
            friendshipRepository.save(friendship);
        } catch (Exception e){
            ErrorsLogger.writeErrorToFile("Friendship", e);
        }
    }

    @Override
    public List<Friendship> getIncomingRequests(User currentUser) {
        try {
            return friendshipRepository.findAllByFriendAndStatus(currentUser, FriendshipStatus.PENDING);
        } catch (Exception e){
            ErrorsLogger.writeErrorToFile("Friendship", e);
        }
        return Collections.emptyList();
    }

    public void acceptRequest(Long id, User currentUser) {
        try {
            Friendship friendship = friendshipRepository.findById(id)
                    .filter(f -> f.getFriend().equals(currentUser) && f.getStatus() == FriendshipStatus.PENDING)
                    .orElseThrow(() -> new FriendshipNotFoundException("Friendship not found"));

            friendship.setStatus(FriendshipStatus.ACCEPTED);
            friendship.setAcceptedAt(LocalDateTime.now());
            friendshipRepository.save(friendship);
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("Friendship", e);
        }
    }

    @Override
    public boolean areFriends(User user1, User user2) {
        try {
            return friendshipRepository.findAllByParticipantAndStatus(user1, FriendshipStatus.ACCEPTED)
                    .stream()
                    .anyMatch(f ->
                            f.getUser().equals(user2) || f.getFriend().equals(user2)
                    );
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("Friendship", e);
        }
        return false;
    }

    public void declineRequest(Long id, User currentUser) {
        try {
            Friendship friendship = friendshipRepository.findById(id)
                    .filter(f -> f.getFriend().equals(currentUser) && f.getStatus() == FriendshipStatus.PENDING)
                    .orElseThrow(() -> new FriendshipNotFoundException("Friendship not found"));

            friendshipRepository.delete(friendship);
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("Friendship", e);
        }
    }

}
