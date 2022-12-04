package ru.practicum.statservice.model;

import org.mapstruct.Mapper;
import ru.practicum.statservice.HitDto;

@Mapper
public interface HitMapper {

    Hit toEntity(HitDto dto);
}
