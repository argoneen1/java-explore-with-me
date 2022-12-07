package ru.practicum.statservice.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.statservice.HitDto;

@Mapper
public interface HitMapper {

    @Mapping(target = "createdOn", source = "timestamp")
    Hit toEntity(HitDto dto);
}
