package ru.itis.uzel.service;

import ru.itis.uzel.dto.MessageDto;
import ru.itis.uzel.dto.MessageForm;
import ru.itis.uzel.entity.User;

import java.util.List;

public interface MessageService {

    MessageDto saveMessage(MessageForm form, User sender, User recipient);

    List<MessageDto> getChatHistory(Long userId, Long friendId);

    void markMessagesAsRead(Long userId, Long friendId);
}
