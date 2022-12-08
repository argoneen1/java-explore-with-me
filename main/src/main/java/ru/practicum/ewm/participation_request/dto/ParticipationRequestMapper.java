package ru.practicum.ewm.participation_request.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.mapper.BaseMapper;
import ru.practicum.ewm.mapper.ReferenceMapper;
import ru.practicum.ewm.participation_request.model.ParticipationRequest;
import ru.practicum.ewm.user.dto.UserMapper;

@Mapper(uses = {UserMapper.class, EventMapper.class, ReferenceMapper.class})
public interface ParticipationRequestMapper extends BaseMapper<
        ParticipationRequestInsertDto,
        ParticipationRequest,
        ParticipationRequestDto> {

    @Mapping(target = "status", constant = "PENDING")
    ParticipationRequest toEntity(ParticipationRequestInsertDto dto);
}
