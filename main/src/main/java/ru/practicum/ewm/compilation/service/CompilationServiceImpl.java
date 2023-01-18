package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.CompilationRepository;
import ru.practicum.ewm.compilation.dto.CompilationInsertDto;
import ru.practicum.ewm.compilation.dto.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.service.EventService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository repository;
    private final CompilationMapper mapper;
    private final EventService eventService;

    @Override
    public Page<Compilation> findAllByPinned(Boolean isPinned, Pageable pageable) {
        return repository.findAllByPinnedOrPinnedIsNull(isPinned, pageable);
    }

    @Override
    public Optional<Compilation> findById(Long id) {
        return repository.findById(id);
    }


    @Override
    public Compilation create(CompilationInsertDto elem) {
        return repository.save(mapper.toEntity(elem));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(Long compilationId, Long eventId) {
        Compilation compilation = findByIdOrThrow(compilationId);
        compilation.deleteEvent(eventId);
        repository.save(compilation);
    }

    @Override
    @Transactional
    public void addEventToCompilation(Long compilationId, Long eventId) {
        Compilation compilation = findByIdOrThrow(compilationId);
        compilation.addEvent(eventService.findByIdOrThrow(eventId));
        repository.save(compilation);
    }

    @Override
    @Transactional
    public void pinCompilationOnMainPage(Long id) {
        Compilation compilation = findByIdOrThrow(id);
        compilation.setPinned(true);
        repository.save(compilation);
    }

    @Override
    @Transactional
    public void detachCompilationFromMainPage(Long id) {
        Compilation compilation = findByIdOrThrow(id);
        compilation.setPinned(false);
        repository.save(compilation);
    }
}
