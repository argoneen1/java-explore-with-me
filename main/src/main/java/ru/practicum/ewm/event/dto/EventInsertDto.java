package ru.practicum.ewm.event.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.validation.NullOrNotBlank;
import ru.practicum.ewm.validation.ValidationMarker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ru.practicum.ewm.event.model.Event} entity
 */
@Data
@NoArgsConstructor
public class EventInsertDto {


    private Long id;

    private Long eventId;

    @Size(min = 3, max = 120, groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class})
    @NotBlank(groups = ValidationMarker.OnCreate.class)
    @NullOrNotBlank(groups = ValidationMarker.OnUpdate.class)
    private String title;

    @Size(min = 20, max = 2000, groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class})
    @NotBlank(groups = ValidationMarker.OnCreate.class)
    @NullOrNotBlank(groups = ValidationMarker.OnUpdate.class)
    private String annotation;

    @NotNull(groups = ValidationMarker.OnCreate.class)
    private Long category;

    @Size(min = 20, max = 7000, groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class})
    @NotBlank(groups = ValidationMarker.OnCreate.class)
    @NullOrNotBlank(groups = ValidationMarker.OnUpdate.class)
    private String description;

    @NotNull(groups = ValidationMarker.OnCreate.class)
    private LocalDateTime eventDate;

    private Long initiatorId;

    @NotNull(groups = ValidationMarker.OnCreate.class)
    private Location location;

    private Boolean paid = false;

    private Integer participantLimit = 0;

    private Boolean requestModeration = true;

}
