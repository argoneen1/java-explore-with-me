package ru.practicum.statservice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public interface StatsClient {
    List<View> getStatistics(LocalDateTime from, LocalDateTime to, List<URI> uris, boolean unique);
    void hit(HitDto dto);
}
