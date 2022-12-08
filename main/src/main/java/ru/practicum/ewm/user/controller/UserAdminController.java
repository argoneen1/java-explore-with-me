package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserInsertDto;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    public List<UserDto> findAll(
            @RequestParam List<Long> ids,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "#{${default-page-size}}") @Positive int size) {
        return service.findAll(ids, PageRequest.of(from / size, size)).stream()
                .map(mapper::toGetDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserInsertDto elem) {
        return mapper.toGetDto(service.create(elem));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
