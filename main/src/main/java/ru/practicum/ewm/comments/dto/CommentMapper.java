package ru.practicum.ewm.comments.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.mapper.BaseMapper;
import ru.practicum.ewm.mapper.ReferenceMapper;

@Mapper(uses = ReferenceMapper.class)
public interface CommentMapper extends BaseMapper<CommentInsertDto, Comment, CommentDto> {
    void update(CommentInsertDto dto, @MappingTarget Comment entity);

    @Mapping(target = "authorId", source = "author.id")
    CommentDto toGetDto(Comment entity);
}
