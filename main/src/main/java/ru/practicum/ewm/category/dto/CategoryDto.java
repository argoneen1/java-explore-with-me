package ru.practicum.ewm.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.validation.ValidationMarker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * A DTO for the {@link ru.practicum.ewm.category.model.Category} entity
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    @Null(groups = ValidationMarker.OnCreate.class)
    @NotNull(groups = ValidationMarker.OnUpdate.class)
    private Long id;

    @NotBlank(groups = {ValidationMarker.OnCreate.class, ValidationMarker.OnUpdate.class})
    private String name;
}