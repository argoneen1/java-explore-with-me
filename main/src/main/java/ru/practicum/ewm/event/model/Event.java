package ru.practicum.ewm.event.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.ewm.base.model.Base;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.model.View;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static ru.practicum.ewm.Configuration.DATE_TIME_FORMAT;

@Getter
@Setter
@Check(constraints = "(events.event_date > CURRENT_TIMESTAMP + (2 * interval '1 hour'))")
@Entity
@Component
@Slf4j
@Table(name = "events")
public class Event extends Base {

    @Column(length = 120, nullable = false)
    private  String title;

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

    @Transient
    private static WebClient client;

    @Autowired
    public void setWebClient(WebClient client) {
        Event.client = client;
    }

    public long getViews() {
        String beginDateString = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now().minusYears(100));
        String endDateString = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(LocalDateTime.now().plusYears(100));
        String uri = "/stats/" +
                "?start=" + beginDateString +
                "&end=" + endDateString +
                "&uris=events/" + getId() +
                "&unique=true";
        View[] views;
        try {
            views = client.get()
                    .uri(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .acceptCharset(StandardCharsets.UTF_8)
                    .retrieve()
                    .onStatus(HttpStatus::isError,
                            r -> r.bodyToMono(String.class)
                                    .map(body -> new ServerException("server doesnt response: " + body)))
                    .bodyToMono(ru.practicum.ewm.model.View[].class)
                    .block();
        } catch (RuntimeException e) {
            log.info("server doesnt response");
            return 0;
        }
        return views == null || views.length == 0 ? 0 : views[0].getHits();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }
}
