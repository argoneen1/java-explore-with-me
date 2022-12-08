package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;


/**
 * A DTO for the {@link ru.practicum.ewm.event.model.Event} entity
 */

@Data
@AllArgsConstructor
public class EventFullDto {
    private long id;
    private LocalDateTime createdOn;
    private String title;
    private String annotation;
    private String description;
    private CategoryDto category;
    private UserShortDto initiator;
    private State state;
    private LocalDateTime eventDate;
    private LocalDateTime publishedOn;
    private boolean paid;
    private boolean requestModeration;
    private int participantLimit;
    private Location location;
    private int confirmedRequests;
    private long views;
}
