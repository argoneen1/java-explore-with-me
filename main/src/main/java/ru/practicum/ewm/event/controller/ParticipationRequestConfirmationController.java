package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.participation_request.dto.ParticipationRequestDto;
import ru.practicum.ewm.participation_request.dto.ParticipationRequestMapper;
import ru.practicum.ewm.participation_request.model.ParticipationRequest;
import ru.practicum.ewm.participation_request.service.ParticipationRequestService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events/{eventId}/requests")
@Validated
public class ParticipationRequestConfirmationController {

    private final ParticipationRequestService service;
    private final ParticipationRequestMapper mapper;
    @GetMapping
    public List<ParticipationRequestDto> findAllByRequesterIdAndEventId(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        List<ParticipationRequest> participationRequests = service.findAllByRequesterIdAndEventId(userId, eventId);
        return participationRequests.stream()
                .map(mapper::toGetDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("{requestId}/confirm")
    public ParticipationRequestDto confirm(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long requestId) {
        return mapper.toGetDto(service.confirm(userId, eventId, requestId));
    }

    @PatchMapping("{requestId}/reject")
    public ParticipationRequestDto reject(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long requestId) {
        return mapper.toGetDto(service.reject(userId, eventId, requestId));
    }
}
