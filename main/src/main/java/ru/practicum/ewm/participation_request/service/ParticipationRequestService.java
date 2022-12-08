package ru.practicum.ewm.participation_request.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.participation_request.dto.ParticipationRequestInsertDto;
import ru.practicum.ewm.participation_request.model.ParticipationRequest;
import ru.practicum.ewm.service.FindByIdService;

import java.util.List;

public interface ParticipationRequestService extends FindByIdService<ParticipationRequest, Long> {

    ParticipationRequest create(ParticipationRequestInsertDto elem);

    Page<ParticipationRequest> findAllByRequesterId(Long id, Pageable pageable);

    List<ParticipationRequest> findAllByRequesterIdAndEventId(Long requesterId, Long eventId);

    int getNumberOfConfirmedRequests(Long eventId);

    ParticipationRequest confirm(Long userId, Long eventId, Long requestId);

    ParticipationRequest reject(Long userId, Long eventId, Long requestId);

    ParticipationRequest cancel(Long userId, Long requesterId);
}
