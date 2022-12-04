package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.stats.Stats;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/events")
@Stats
public class EventPublicController {

    private final EventService service;
    private final EventMapper mapper;

    @GetMapping
    public List<EventShortDto> findAll(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) SortOption sort,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "#{${default-page-size}}") @Positive int size,
            HttpServletRequest request
    ) {
        Pageable pageable;
        if (sort == null) {
            pageable = PageRequest.of(from / size, size);
        } else {
            pageable = PageRequest.of(from / size, size, Sort.Direction.DESC, sort.entityFieldName);
        }
        return service.findAll(text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                pageable
        ).getContent().stream().map(mapper::toEventShortDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EventFullDto findByIdPublished(@PathVariable Long id, HttpServletRequest request) {
        return mapper.toGetDto(service.findByIdPublished(id)
                .orElseThrow(() -> new NoSuchElementException("there is no such event with id " + id)));
    }

    public enum SortOption {
        EVENT_DATE("eventDate"),
        VIEWS("views");

        public final String entityFieldName;

        SortOption(String entityFieldName) {
            this.entityFieldName = entityFieldName;
        }
    }
}
