package ru.practicum.ewm.mapper;

import ru.practicum.ewm.model.Base;

public interface BaseMapper<I, E extends Base, G> {

    E toEntity(Long id);

    E toEntity(I dto);

    G toGetDto(E entity);

    default Long toId(E entity) {
        return entity.getId();
    }
}
