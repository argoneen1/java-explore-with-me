package ru.practicum.ewm.participation_request.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.model.Base;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "participation_requests")
public class ParticipationRequest extends Base {

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "id", nullable = false)
    private User requester;

    @Enumerated
    @Column
    private Status status;

    public ParticipationRequest() {
        super();
    }

}