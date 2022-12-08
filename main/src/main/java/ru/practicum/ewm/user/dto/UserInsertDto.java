package ru.practicum.ewm.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * A DTO for the {@link ru.practicum.ewm.user.model.User} entity
 */
@Data
@NoArgsConstructor
public class UserInsertDto {

    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;
}
