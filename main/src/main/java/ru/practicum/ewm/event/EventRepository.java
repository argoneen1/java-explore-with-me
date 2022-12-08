package ru.practicum.ewm.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAllByInitiator_Id(Long id, Pageable pageable);

    @Query("from Event e " +
            "where (:paid is null or e.paid = :paid) and " +
            "e.state = ru.practicum.ewm.event.model.State.PUBLISHED and " +
            "(e.eventDate > :rangeStart and (cast(:rangeEnd as timestamp) is null or e.eventDate < :rangeEnd)) and " +
            "(:categories is null or e.category.id in :categories) and " +
            "(upper(e.annotation) like upper(concat('%', :searchText, '%')) or " +
            "   upper(e.description) like upper(concat('%', :searchText, '%'))) and " +
            "(:onlyAvailable = false or (select count(p) from ParticipationRequest p where p.event.id = e.id) < e.participantLimit)")
    Page<Event> findAllBySearch(String searchText,
                                List<Long> categories,
                                Boolean paid,
                                LocalDateTime rangeStart,
                                LocalDateTime rangeEnd,
                                Boolean onlyAvailable,
                                Pageable pageable
    );

    @Query("from Event e " +
            "where " +
            "(e.eventDate > :rangeStart and (cast(:rangeEnd as timestamp) is null or e.eventDate < :rangeEnd)) and " +
            "(:users is null or e.initiator.id in :users) and " +
            "(:states is null or e.state in :states) and " +
            "(:categories is null or e.category.id in :categories)")
    Page<Event> findAllFromAdmin(
            List<Long> users,
            List<State> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Pageable pageable
    );

    Optional<Event> findByIdAndState(Long id, State state);
}
