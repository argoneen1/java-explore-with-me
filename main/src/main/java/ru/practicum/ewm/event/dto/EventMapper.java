package ru.practicum.ewm.event.dto;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.ewm.base.BaseMapper;
import ru.practicum.ewm.base.ReferenceMapper;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.dto.UserMapper;

@Mapper(uses = {CategoryMapper.class, UserMapper.class, ReferenceMapper.class})
public abstract class EventMapper implements BaseMapper<EventInsertDto, Event, EventFullDto>{

    @Autowired
    protected WebClient webClient;

    @Mapping(target = "initiator", source = "initiatorId")
    @Mapping(target = "state", constant = "PENDING")
    public abstract Event toEntity(EventInsertDto dto);

    public abstract EventShortDto toEventShortDto(Event entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "initiator", source = "initiatorId")
    public abstract void update(EventInsertDto dto, @MappingTarget Event event);
}
