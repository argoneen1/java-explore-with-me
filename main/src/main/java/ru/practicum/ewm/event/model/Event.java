package ru.practicum.ewm.event.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.base.model.Base;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.user.model.User;
import ru.practicum.statservice.StatsClient;
import ru.practicum.statservice.View;

import javax.persistence.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Check(constraints = "(events.event_date > CURRENT_TIMESTAMP + (2 * interval '1 hour'))")
@Entity
@Component
@Slf4j
@Table(name = "events")
public class Event extends Base {

    @Transient
    private static StatsClient client;
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
    @Formula("(SELECT COUNT(pr.event_id) FROM participation_requests AS pr WHERE pr.event_id = id)")
    private Integer confirmedRequests;
    @ManyToMany(mappedBy = "events")
    private Set<Compilation> eventCompilations;

    public Event() {
        super();
    }

    @Autowired
    public void setStatsClient(StatsClient client) {
        Event.client = client;
    }

    public long getViews() {
        List<View> views = client.getStatistics(
                LocalDateTime.now().minusYears(100),
                LocalDateTime.now().plusYears(100),
                List.of(URI.create("events/" + getId())),
                true);
        return views == null || views.size() == 0 ? 0 : views.get(0).getHits();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }
}
