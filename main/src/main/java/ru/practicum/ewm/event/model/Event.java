package ru.practicum.ewm.event.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.model.Base;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Check(constraints = "(events.event_date > CURRENT_TIMESTAMP + (2 * interval '1 hour'))")
@Entity
@Component
@Slf4j
@Table(name = "events")
public class Event extends Base {
    @Column(length = 120, nullable = false)
    private String title;
    @Column(length = 2000, nullable = false)
    private String annotation;
    @Column(length = 7000, nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id", nullable = false)
    private User initiator;
    @Enumerated
    @Column
    private State state;
    @Column(nullable = false)
    private LocalDateTime eventDate;
    @Column
    private LocalDateTime publishedOn;
    @Column(nullable = false)
    private Boolean paid;
    @Column
    private Boolean requestModeration;
    @Column
    private Integer participantLimit;
    @Embedded
    private Location location;
    @ManyToMany(mappedBy = "events")
    private Set<Compilation> eventCompilations;

    public Event() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }
}
