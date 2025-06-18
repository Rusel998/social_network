package ru.itis.uzel.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.uzel.dto.MessageDto;
import ru.itis.uzel.dto.MessageForm;
import ru.itis.uzel.entity.Message;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.mapper.MessageMapper;
import ru.itis.uzel.repository.MessageRepository;
import ru.itis.uzel.service.MessageService;
import ru.itis.uzel.util.ErrorsLogger;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public MessageDto saveMessage(MessageForm form, User sender, User recipient) {
        try {
            Message message = messageMapper.toEntity(form, sender, recipient);
            message.setUpdatedAt(LocalDateTime.now());
            message = messageRepository.save(message);
            return messageMapper.toDto(message);
        } catch (Exception e){
            ErrorsLogger.writeErrorToFile("Message", e);
        }
        return null;
    }

    @Override
    public List<MessageDto> getChatHistory(Long userId, Long friendId) {
        try {
            return messageRepository.findChatHistory(userId, friendId)
                    .stream()
                    .map(messageMapper::toDto)
                    .toList();
        } catch (Exception e) {
            ErrorsLogger.writeErrorToFile("Message", e);
        }
        return null;
    }

    @Override
    public void markMessagesAsRead(Long userId, Long friendId) {
        try {
            messageRepository.markMessagesAsRead(userId, friendId);
        } catch (Exception e){
            ErrorsLogger.writeErrorToFile("Message", e);
        }
    }
}
