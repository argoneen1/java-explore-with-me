package ru.practicum.ewm.model;

import org.mapstruct.Mapper;

@Mapper
public interface HitMapper {

    Hit toEntity(HitDto dto);
}
