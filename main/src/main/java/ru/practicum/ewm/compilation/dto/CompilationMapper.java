package ru.practicum.ewm.compilation.dto;

import org.mapstruct.Mapper;
import ru.practicum.ewm.base.BaseMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.dto.EventMapper;

@Mapper(uses = EventMapper.class)
public interface CompilationMapper extends BaseMapper<CompilationInsertDto, Compilation, CompilationDto> {
}
