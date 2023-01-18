package ru.practicum.ewm.event.dto;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.mapper.BaseMapper;
import ru.practicum.ewm.mapper.ReferenceMapper;
import ru.practicum.ewm.participation_request.service.ParticipationRequestService;
import ru.practicum.ewm.user.dto.UserMapper;

@Mapper(uses = {CategoryMapper.class, UserMapper.class, ReferenceMapper.class})
public abstract class EventMapper implements BaseMapper<EventInsertDto, Event, EventFullDto> {

    @Autowired
    @Lazy
    protected EventService service;

    @Autowired
    @Lazy
    protected ParticipationRequestService participationRequestService;

    @Mapping(target = "initiator", source = "initiatorId")
    @Mapping(target = "state", constant = "PENDING")
    public abstract Event toEntity(EventInsertDto dto);

    @Mapping(target = "confirmedRequests",
            expression = "java(participationRequestService.getNumberOfConfirmedRequests(entity.getId()))")
    @Mapping(target = "views", expression = "java(service.getViews(entity.getId()))")
    @Named("short")
    public abstract EventShortDto toEventShortDto(Event entity);

    @Mapping(target = "confirmedRequests",
            expression = "java(participationRequestService.getNumberOfConfirmedRequests(entity.getId()))")
    @Mapping(target = "views", expression = "java(service.getViews(entity.getId()))")
    @Named("full")
    public abstract EventFullDto toGetDto(Event entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "initiator", source = "initiatorId")
    public abstract void update(EventInsertDto dto, @MappingTarget Event event);

}
