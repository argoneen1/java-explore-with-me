package ru.practicum.statservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statservice.model.HitMapper;
import ru.practicum.statservice.repository.StatsRepository;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final StatsRepository repository;
    private final HitMapper mapper;

    @PostMapping("/hit")
    public void hit(@RequestBody HitDto hit) {
        log.info("hit executed with {}", hit);
        repository.save(mapper.toEntity(hit));
    }

    @GetMapping("/stats")
    public List<View> getStats(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            @RequestParam List<URI> uris,
            @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.info("get stats, from: {}, to: {}, uris: {}, unique: {}, returned values: {}", start, end, uris, unique, repository.findAllByRange(start, end, uris, unique));
        return repository.findAllByRange(start, end, uris, unique);
    }
}
