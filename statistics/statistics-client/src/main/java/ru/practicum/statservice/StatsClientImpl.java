package ru.practicum.statservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static ru.practicum.ewm.Configuration.DATE_TIME_FORMAT;

@Slf4j
public class StatsClientImpl implements StatsClient {

    private final WebClient client;

    public StatsClientImpl(WebClient.Builder client) {
        this.client = client.build();
    }

    @Override
    public List<View> getStatistics(LocalDateTime from, LocalDateTime to, List<URI> uris, boolean unique) {
        String beginDateString = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(from);
        String endDateString = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(to);

        StringBuilder builder = new StringBuilder("http://localhost:9090/stats/");
        builder.append("?start=").append(beginDateString)
                .append("&end=").append(endDateString)
                .append("&unique=").append(unique);
        uris.forEach(uri -> builder.append("&uris=").append(uri.toString()));

        View[] views;
        try {
            views = client.get()
                    .uri(builder.toString())
                    .accept(MediaType.APPLICATION_JSON)
                    .acceptCharset(StandardCharsets.UTF_8)
                    .retrieve()
                    .onStatus(HttpStatus::isError,
                            r -> r.bodyToMono(String.class)
                                    .map(body -> new ServerException("server doesnt response: " + body)))
                    .bodyToMono(View[].class)
                    .block();
        } catch (RuntimeException e) {
            log.info("server doesnt response");
            return List.of();
        }
        return views == null ? List.of() : Arrays.asList(views);
    }

    @Override
    public void hit(HitDto dto) {
        client.post()
                .uri("http://localhost:9090/hit")
                .body(BodyInserters.fromValue(dto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
