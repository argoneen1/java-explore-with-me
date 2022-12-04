package ru.practicum.ewm.participation_request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.participation_request.ParticipationRequestRepository;
import ru.practicum.ewm.participation_request.dto.ParticipationRequestInsertDto;
import ru.practicum.ewm.participation_request.dto.ParticipationRequestMapper;
import ru.practicum.ewm.participation_request.model.ParticipationRequest;
import ru.practicum.ewm.participation_request.model.Status;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository repository;
    private final ParticipationRequestMapper mapper;
    private final EventService eventService;
    private final UserService userService;

    @Override
    public ParticipationRequest create(ParticipationRequestInsertDto elem) {
        return repository.save(mapper.toEntity(elem));
    }

    @Override
    public Page<ParticipationRequest> findAllByRequesterId(Long id, Pageable pageable) {
        return repository.findAllByRequesterId(id, pageable);
    }

    @Override
    public List<ParticipationRequest> findAllByRequesterIdAndEventId(Long userId, Long eventId) {
        if (!eventService
                .findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("there is no event with id " + eventId))
                .getInitiator()
                .getId()
                .equals(userId)){
            throw new IllegalArgumentException();
        }
        return repository.findAllByEventId(eventId);
    }

    @Override
    public Optional<ParticipationRequest> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ParticipationRequest confirm(Long userId, Long eventId, Long requestId) {
        Event event = eventService.findByIdOrThrow(eventId);
        int confirmedRequests = event.getConfirmedRequests();
        if (confirmedRequests >= event.getParticipantLimit()) {
            throw new IllegalStateException("there are no free requests");
        }
        ParticipationRequest request = findByIdOrThrow(requestId);
        requestConfirmationValidate(userId, event, request);
        if (confirmedRequests == event.getParticipantLimit() - 1) {
            for (ParticipationRequest rejectedRequest :
                    repository.findAllByEventIdAndStatusEquals(eventId, Status.PENDING)) {
                rejectedRequest.setStatus(Status.REJECTED);
                repository.save(rejectedRequest);
            }
        }
        request.setStatus(Status.CONFIRMED);
        return repository.save(request);
    }

    @Override
    public ParticipationRequest reject(Long userId, Long eventId, Long requestId) {
        ParticipationRequest request = findByIdOrThrow(requestId);
        requestConfirmationValidate(userId, eventService.findByIdOrThrow(eventId), request);
        request.setStatus(Status.REJECTED);
        return repository.save(request);
    }

    private void requestConfirmationValidate(Long userId, Event event, ParticipationRequest request) {
        if (!request.getEvent().getId().equals(event.getId())) {
            throw new IllegalArgumentException("wrong request");
        }
        User user = userService.findByIdOrThrow(userId);
        if (!event.getInitiator().getId().equals(user.getId())) {
            throw new IllegalArgumentException("only event initiator can confirm request");
        }
    }

    @Override
    public ParticipationRequest cancel(Long userId, Long requestId) {
        ParticipationRequest request = findByIdOrThrow(requestId);
        if (!request.getRequester().getId().equals(userId)) {
            throw new IllegalArgumentException("only requester can cancel own request");
        }
        request.setStatus(Status.CANCELED);
        return repository.save(request);
    }
}
