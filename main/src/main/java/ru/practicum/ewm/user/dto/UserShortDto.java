package ru.practicum.ewm.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.practicum.ewm.user.model.User} entity
 */
@Data
public class UserShortDto implements Serializable {
    private final Long id;
    private final String name;
}