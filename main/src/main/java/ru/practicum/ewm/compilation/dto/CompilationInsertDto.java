package ru.practicum.ewm.compilation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * A DTO for the {@link ru.practicum.ewm.compilation.model.Compilation} entity
 */
@Data
@NoArgsConstructor
public class CompilationInsertDto {

    @NotBlank
    private String title;

    private Boolean pinned;

    private Set<Long> events;
}
