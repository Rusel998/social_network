package ru.itis.uzel.web.controller.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.itis.uzel.dto.MessageDto;
import ru.itis.uzel.dto.MessageForm;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.repository.UserRepository;
import ru.itis.uzel.service.MessageService;


@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final UserRepository userRepository;

    @MessageMapping("/chat.send")
    public void handleMessage(@Payload MessageDto dto) {
        User sender = userRepository.findById(dto.getSenderId()).orElseThrow();
        User recipient = userRepository.findById(dto.getRecipientId()).orElseThrow();

        MessageForm form = new MessageForm();
        form.setRecipientId(dto.getRecipientId());
        form.setContent(dto.getContent());

        MessageDto saved = messageService.saveMessage(form, sender, recipient);

        messagingTemplate.convertAndSendToUser(
                recipient.getId().toString(), "/topic/messages", saved
        );
        messagingTemplate.convertAndSendToUser(
                sender.getId().toString(), "/topic/messages", saved
        );
    }
}
