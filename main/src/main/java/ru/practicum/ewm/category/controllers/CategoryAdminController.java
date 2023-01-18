package ru.practicum.ewm.category.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.validation.ValidationMarker;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/categories")
public class CategoryAdminController {

    private final CategoryService service;
    private final CategoryMapper mapper;

    @PostMapping
    @Validated(ValidationMarker.OnCreate.class)
    public CategoryDto create(@Valid @RequestBody CategoryDto elem) {
        return mapper.toGetDto(service.create(elem));
    }

    @PatchMapping
    @Validated(ValidationMarker.OnUpdate.class)
    public CategoryDto update(@Valid @RequestBody CategoryDto elem) {
        return mapper.toGetDto(service.update(elem));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
