package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.CompilationInsertDto;
import ru.practicum.ewm.compilation.dto.CompilationMapper;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/compilations")
public class CompilationAdminController {

    private final CompilationService service;
    private final CompilationMapper mapper;

    @PostMapping
    public CompilationDto create(@Valid @RequestBody CompilationInsertDto elem) {
        return mapper.toGetDto(service.create(elem));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable Long compId, @PathVariable Long eventId) {
        service.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long compId, @PathVariable Long eventId) {
        service.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{id}/pin")
    public void pinCompilationOnMainPage(@PathVariable Long id) {
        service.pinCompilationOnMainPage(id);
    }

    @DeleteMapping("/{id}/pin")
    public void detachCompilationFromMainPage(@PathVariable Long id) {
        service.detachCompilationFromMainPage(id);
    }
}
