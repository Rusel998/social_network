package ru.itis.uzel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.uzel.dto.MessageDto;
import ru.itis.uzel.dto.MessageForm;
import ru.itis.uzel.entity.Message;
import ru.itis.uzel.entity.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isRead", constant = "false")
    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "recipient", source = "recipient")
    Message toEntity(MessageForm form, User sender, User recipient);

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "recipientId", source = "recipient.id")
    @Mapping(target = "sentAt", source = "message.sentAt")
    MessageDto toDto(Message message);
}