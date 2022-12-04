package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.EventRepository;
import ru.practicum.ewm.event.dto.EventInsertDto;
import ru.practicum.ewm.event.dto.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository repository;
    private final EventMapper mapper;

    @Override
    public Page<Event> findAll(String searchText,
                               List<Long> categories,
                               Boolean paid,
                               LocalDateTime rangeStart,
                               LocalDateTime rangeEnd,
                               Boolean onlyAvailable,
                               Pageable pageable) {
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        return repository.findAllBySearch(searchText, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable);
    }

    @Override
    public Event create(EventInsertDto elem) {
        return repository.save(mapper.toEntity(elem));
    }

    @Override
    public Event update(EventInsertDto elem, boolean isValidated) {
        Event event = findByIdOrThrow(elem.getEventId());
        if (isValidated) {
            validate(event);
        }
        mapper.update(elem, event);
        return repository.save(event);
    }

    private void validate(Event event) {
        if (event.getState() != State.PENDING && event.getState() != State.CANCELED) {
            throw new IllegalArgumentException("event status is not \"pending\" or \"canceled\"");
        }
    }

    @Override
    public Page<Event> findAllByInitiatorId(Long id, Pageable pageable) {
        return repository.findAllByInitiator_Id(id, pageable);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Event> findByIdPublished(Long id) {
        return repository.findByIdAndState(id, State.PUBLISHED);
    }

    @Override
    public Optional<Event> findByInitiatorIdAndId(Long initiatorId, Long id) {
        Optional<Event> event = findById(id);
        if (event.isEmpty()) {
            return Optional.empty();
        }
        if (!event.get().getInitiator().getId().equals(initiatorId)) {
            throw new IllegalArgumentException("initiator id and requester id do not matches");
        }
        return event;
    }

    @Override
    public Optional<Event> cancelEvent(Long initiatorId, Long id) {
        Optional<Event> event = findByInitiatorIdAndId(initiatorId, id);
        if (event.isEmpty()) {
            return Optional.empty();
        }
        event.get().setState(State.CANCELED);
        repository.save(event.get());
        return findById(id);
    }

    @Override
    public Page<Event> findAllFromAdmin(
            List<Long> users,
            List<State> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable) {
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        return repository.findAllFromAdmin(
                users,
                states,
                categories,
                rangeStart,
                rangeEnd,
                pageable
        );
    }

    @Override
    public Event publish(Long id) {
        Event event = findByIdOrThrow(id);
        event.setState(State.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        return repository.save(event);
    }

    @Override
    public Event reject(Long id) {
        Event event = findByIdOrThrow(id);
        event.setState(State.CANCELED);
        return repository.save(event);
    }
}
