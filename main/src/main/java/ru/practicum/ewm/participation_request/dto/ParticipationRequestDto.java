package ru.practicum.ewm.participation_request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.participation_request.model.Status;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link ru.practicum.ewm.participation_request.model.ParticipationRequest} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    Long id;
    LocalDateTime created;
    Long event;
    Long requester;
    Status status;
}
