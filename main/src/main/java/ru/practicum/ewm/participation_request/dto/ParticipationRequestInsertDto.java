package ru.practicum.ewm.participation_request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link ru.practicum.ewm.participation_request.model.ParticipationRequest} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestInsertDto {
    private Long requester;
    private Long event;
}
