package ru.itis.uzel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.uzel.dto.PostForm;
import ru.itis.uzel.entity.FileInfo;
import ru.itis.uzel.entity.Post;
import ru.itis.uzel.entity.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", source = "author")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "likedBy", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "image", source = "imageFile")
    Post toEntity(PostForm form, User author, FileInfo imageFile);
}
