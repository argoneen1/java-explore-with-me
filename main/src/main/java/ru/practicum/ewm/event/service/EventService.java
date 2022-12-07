package ru.practicum.ewm.event.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.event.dto.EventInsertDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.service.FindByIdService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService extends FindByIdService<Event, Long> {
    Page<Event> findAll(String searchText,
                        List<Long> categories,
                        Boolean paid,
                        LocalDateTime rangeStart,
                        LocalDateTime rangeEnd,
                        Boolean onlyAvailable,
                        Pageable pageable);

    Event create(EventInsertDto elem);

    Event update(EventInsertDto elem, boolean isValidated);

    Page<Event> findAllByInitiatorId(Long id, Pageable pageable);

    Optional<Event> findByIdPublished(Long id);

    Optional<Event> findByInitiatorIdAndId(Long initiatorId, Long id);

    Optional<Event> cancelEvent(Long initiatorId, Long id);

    Page<Event> findAllFromAdmin(
            List<Long> users,
            List<State> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    Event publish(Long id);

    Event reject(Long id);
}
