package ru.practicum.ewm.compilation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.compilation.dto.CompilationInsertDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.service.FindByIdService;

public interface CompilationService extends FindByIdService<Compilation, Long> {

    Page<Compilation> findAllByPinned(Boolean isPinned, Pageable pageable);

    Compilation create(CompilationInsertDto elem);

    void deleteById(Long id);

    void deleteEventFromCompilation(Long compilationId, Long eventId);

    void addEventToCompilation(Long compilationId, Long eventId);

    void pinCompilationOnMainPage(Long id);

    void detachCompilationFromMainPage(Long id);

}
