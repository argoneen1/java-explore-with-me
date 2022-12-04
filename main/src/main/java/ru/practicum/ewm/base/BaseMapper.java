package ru.practicum.ewm.base;

import ru.practicum.ewm.base.model.Base;

public interface BaseMapper <I,E extends Base,G> {

    E toEntity(Long id);

    E toEntity(I dto);

    G toGetDto(E entity);

    default Long toId(E entity){
        return entity.getId();
    }
}
