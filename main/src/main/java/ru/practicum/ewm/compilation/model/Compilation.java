package ru.practicum.ewm.compilation.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.model.Base;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "event_compilations")
public class Compilation extends Base {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "event_compilation_event",
            joinColumns = @JoinColumn(name = "event_compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events;

    @Column
    private boolean pinned;

    @Column
    private String title;

    public Compilation() {
        super();
        events = new HashSet<>();
    }

    public boolean addEvent(Event event) {
        return events.add(event);
    }

    public boolean deleteEvent(Long id) {
        return events.removeIf(e -> e.getId().equals(id));
    }

}
