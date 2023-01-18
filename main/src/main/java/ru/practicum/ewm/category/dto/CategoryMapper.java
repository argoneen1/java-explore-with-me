package ru.practicum.ewm.category.dto;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.mapper.BaseMapper;
import ru.practicum.ewm.mapper.ReferenceMapper;


@Mapper(uses = ReferenceMapper.class)
public interface CategoryMapper extends BaseMapper<CategoryDto, Category, CategoryDto> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(CategoryDto dto, @MappingTarget Category entity);

}
