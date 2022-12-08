package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link ru.practicum.ewm.user.model.User} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    private String name;
    private String email;
}
