package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link ru.practicum.ewm.event.model.Event} entity
 */

@Data
@AllArgsConstructor
public class EventShortDto {

    private long id;
    private LocalDateTime createdOn;
    private String title;
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private boolean paid;
    private long views;
}
