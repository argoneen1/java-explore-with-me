package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventInsertDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.validation.ValidationMarker;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Validated
public class EventPrivateController {

    private final EventService service;
    private final EventMapper mapper;

    @GetMapping
    public List<EventShortDto> findAllByInitiatorId(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "#{${default-page-size}}") @Positive int size
    ) {
        return service.findAllByInitiatorId(userId, PageRequest.of(from / size, size))
                .stream().map(mapper::toEventShortDto).collect(Collectors.toList());
    }

    @PostMapping
    @Validated(ValidationMarker.OnCreate.class)
    public EventFullDto create(@Valid @RequestBody EventInsertDto elem, @PathVariable Long userId) {
        elem.setInitiatorId(userId);
        return mapper.toGetDto(service.create(elem));
    }

    @PatchMapping
    @Validated(ValidationMarker.OnUpdate.class)
    public EventFullDto update(@Valid @RequestBody EventInsertDto elem, @PathVariable Long userId) {
        elem.setInitiatorId(userId);
        return mapper.toGetDto(service.update(elem, true));
    }

    @GetMapping("/{eventId}")
    public EventFullDto findByInitiatorIdAndId(@PathVariable Long userId, @PathVariable Long eventId) {
        return mapper.toGetDto(service.findByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new NoSuchElementException("there is no such event with id" + eventId)));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        return mapper.toGetDto(service.cancelEvent(userId, eventId)
                .orElseThrow(() -> new NoSuchElementException("there is no such event with id" + eventId)));
    }
}
