package ru.practicum.ewm.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.ewm.model.HitDto;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class StatAspect {

    WebClient client;

    @Pointcut("within(@ru.practicum.ewm.stats.Stats *)")
    public void callAt() {
    }

    @Before("callAt()")
    public void before(JoinPoint pjp) {
        Arrays.stream(pjp.getArgs())
                .filter(o -> o instanceof HttpServletRequest)
                .map(o -> (HttpServletRequest) o)
                .findAny().ifPresent(request ->  {
                    HitDto hit;
                    try {
                        hit = new HitDto("main",
                                URI.create(request.getRequestURI()),
                                InetAddress.getByName(request.getRemoteAddr()),
                                LocalDateTime.now());
                    } catch (UnknownHostException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        client.post()
                                .uri("/hit")
                                .body(BodyInserters.fromValue(hit))
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .acceptCharset(StandardCharsets.UTF_8)
                                .retrieve()
                                .bodyToMono(Void.class)
                                .subscribe();
                    } catch (RuntimeException e) {
                        log.info(e.getMessage());
                    }
                });
    }

    @Override
    public int hashCode() {
        return Objects.hash(client);
    }
}
