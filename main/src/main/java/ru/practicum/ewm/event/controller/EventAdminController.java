package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventInsertDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Validated
public class EventAdminController {

    private final EventService service;
    private final EventMapper mapper;

    @GetMapping("/admin/events")
    public List<EventFullDto> findAllFromAdmin(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<State> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false,
                    defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "#{${default-page-size}}") @Positive int size
    ) {
        return service.findAllFromAdmin(
                        users,
                        states,
                        categories,
                        rangeStart,
                        rangeEnd,
                        PageRequest.of(from / size, size)).stream()
                .map(mapper::toGetDto).collect(Collectors.toList());
    }

    @PutMapping("/admin/events/{id}")
    public EventFullDto updateFromAdmin(@RequestBody EventInsertDto elem, @PathVariable Long id) {
        elem.setEventId(id);
        return mapper.toGetDto(service.update(elem, false));
    }

    @PatchMapping("/admin/events/{id}/publish")
    public EventFullDto publishEvent(@PathVariable Long id) {
        return mapper.toGetDto(service.publish(id));
    }

    @PatchMapping("/admin/events/{id}/reject")
    public EventFullDto rejectEvent(@PathVariable Long id) {
        return mapper.toGetDto(service.reject(id));
    }

}
