package ru.practicum.ewm.participation_request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.participation_request.dto.ParticipationRequestDto;
import ru.practicum.ewm.participation_request.dto.ParticipationRequestInsertDto;
import ru.practicum.ewm.participation_request.dto.ParticipationRequestMapper;
import ru.practicum.ewm.participation_request.service.ParticipationRequestService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
@Validated
public class ParticipationRequestPrivateController {

    private final ParticipationRequestService service;
    private final ParticipationRequestMapper mapper;

    @PostMapping
    public ParticipationRequestDto create(@PathVariable Long userId, @RequestParam Long eventId) {
        return mapper.toGetDto(service.create(new ParticipationRequestInsertDto(userId, eventId)));
    }

    @GetMapping
    public List<ParticipationRequestDto> findById(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "#{${default-page-size}}") @Positive int size
    ) {
        return service.findAllByRequesterId(userId, PageRequest.of(from / size, size)).stream()
                .map(mapper::toGetDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        return mapper.toGetDto(service.cancel(userId, requestId));
    }

}
