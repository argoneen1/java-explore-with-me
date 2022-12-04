package ru.practicum.ewm.participation_request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.participation_request.model.ParticipationRequest;
import ru.practicum.ewm.participation_request.model.Status;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    Page<ParticipationRequest> findAllByRequesterId(Long id, Pageable pageable);

    List<ParticipationRequest> findAllByEventIdAndStatusEquals(Long event_id, Status status);

    List<ParticipationRequest> findAllByEventId(Long eventId);

}
