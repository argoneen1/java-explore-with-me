package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.CompilationMapper;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/compilations")
public class CompilationPublicController {

    private final CompilationService service;
    private final CompilationMapper mapper;
    @GetMapping
    public List<CompilationDto> findAllByPinned(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "#{${default-page-size}}") @Positive int size) {
        return service.findAllByPinned(pinned, PageRequest.of(from / size, size)).stream()
                .map(mapper::toGetDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompilationDto findById(@PathVariable Long id) {
        return mapper.toGetDto(service.findById(id)
                .orElseThrow(() -> new NoSuchElementException("there is no such compilation with id " + id)));
    }
}
