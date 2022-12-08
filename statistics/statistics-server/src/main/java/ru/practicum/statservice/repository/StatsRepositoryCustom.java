package ru.practicum.statservice.repository;

import ru.practicum.statservice.View;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepositoryCustom {

    List<View> findAllByRange(LocalDateTime start, LocalDateTime end, List<URI> uris, Boolean unique);

}
