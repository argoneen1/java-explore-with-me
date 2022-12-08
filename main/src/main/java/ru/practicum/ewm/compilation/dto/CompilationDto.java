package ru.practicum.ewm.compilation.dto;

import lombok.Data;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link ru.practicum.ewm.compilation.model.Compilation} entity
 */
@Data
public class CompilationDto {
    private Long id;
    private String title;
    private Boolean pinned;
    private List<EventShortDto> events;

    public CompilationDto(Long id, String title, Boolean pinned) {
        this.id = id;
        this.title = title;
        this.pinned = pinned;
        events = new ArrayList<>();
    }

    public void addEvent(EventShortDto event) {
        events.add(event);
    }
}
