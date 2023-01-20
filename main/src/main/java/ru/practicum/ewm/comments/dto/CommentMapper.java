package ru.practicum.ewm.comments.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.mapper.BaseMapper;
import ru.practicum.ewm.mapper.ReferenceMapper;
import ru.practicum.ewm.user.dto.UserMapper;

@Mapper(uses = {ReferenceMapper.class, UserMapper.class, EventMapper.class})
public interface CommentMapper extends BaseMapper<CommentInsertDto, Comment, CommentDto> {
    void update(CommentInsertDto dto, @MappingTarget Comment entity);
}
