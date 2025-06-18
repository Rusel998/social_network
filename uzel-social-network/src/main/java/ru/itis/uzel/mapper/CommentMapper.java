package ru.itis.uzel.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.uzel.dto.CommentDto;
import ru.itis.uzel.dto.CommentForm;
import ru.itis.uzel.entity.Comment;
import ru.itis.uzel.entity.Post;
import ru.itis.uzel.entity.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "likedBy", ignore = true)
    @Mapping(target = "author", source = "author")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "content", source = "form.content")
    Comment toEntity(CommentForm form, User author, Post post);

    @Mapping(target = "id", source = "comment.id")
    @Mapping(target = "text", source = "comment.content")
    @Mapping(target = "authorName", expression = "java(comment.getAuthor().getFirstName() + \" \" + comment.getAuthor().getLastName())")
    @Mapping(target = "authorId", source = "comment.author.id")
    @Mapping(target = "createdAt", source = "comment.createdAt")
    @Mapping(target = "likeCount", expression = "java(comment.getLikedBy().size())")
    @Mapping(target = "likedByCurrentUser", expression = "java(comment.getLikedBy().contains(currentUser))")
    @Mapping(target = "deletable", expression = "java(comment.getPost().getAuthor().getId().equals(currentUser.getId()))")
    CommentDto toDto(Comment comment, User currentUser);

}
