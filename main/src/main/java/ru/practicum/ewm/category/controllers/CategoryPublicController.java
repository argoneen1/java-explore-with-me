package ru.practicum.ewm.category.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/categories")
public class CategoryPublicController {

    private final CategoryService service;
    private final CategoryMapper mapper;

    @GetMapping
    public List<CategoryDto> findAll(
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "#{${default-page-size}}") @Positive int size) {
        return service.findAll(PageRequest.of(from / size, size))
                .getContent().stream()
                .map(mapper::toGetDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable long id) {
        return service.findById(id).map(mapper::toGetDto)
                .orElseThrow(() -> new NoSuchElementException("there is no such category with id " + id));
    }

}
