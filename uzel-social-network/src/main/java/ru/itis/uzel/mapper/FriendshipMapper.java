package ru.itis.uzel.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.uzel.dto.FriendshipForm;
import ru.itis.uzel.entity.Friendship;
import ru.itis.uzel.entity.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FriendshipMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "acceptedAt", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "user", source = "sender")
    @Mapping(target = "friend", source = "receiver")
    Friendship toEntity(FriendshipForm form, User sender, User receiver);
}
