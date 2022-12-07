package ru.practicum.ewm.event.dto;

import org.mapstruct.*;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.mapper.BaseMapper;
import ru.practicum.ewm.mapper.ReferenceMapper;
import ru.practicum.ewm.user.dto.UserMapper;

@Mapper(uses = {CategoryMapper.class, UserMapper.class, ReferenceMapper.class})
public interface EventMapper extends BaseMapper<EventInsertDto, Event, EventFullDto> {


    @Mapping(target = "initiator", source = "initiatorId")
    @Mapping(target = "state", constant = "PENDING")
    Event toEntity(EventInsertDto dto);

    EventShortDto toEventShortDto(Event entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "initiator", source = "initiatorId")
    void update(EventInsertDto dto, @MappingTarget Event event);
}
