package ru.practicum.ewm.user.dto;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.base.BaseMapper;
import ru.practicum.ewm.base.ReferenceMapper;
import ru.practicum.ewm.user.model.User;

@Mapper(uses = ReferenceMapper.class)
public interface UserMapper extends BaseMapper<UserInsertDto, User, UserDto> {

    UserShortDto toUserShortDto(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(UserInsertDto dto, @MappingTarget User entity);

}
