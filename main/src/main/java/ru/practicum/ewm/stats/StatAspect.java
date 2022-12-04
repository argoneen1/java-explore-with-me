package ru.practicum.ewm.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.practicum.statservice.HitDto;
import ru.practicum.statservice.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class StatAspect {

    private final StatsClient client;

    @Pointcut("within(@ru.practicum.ewm.stats.Stats *)")
    public void callAt() {
    }

    @Before("callAt()")
    public void before(JoinPoint pjp) {
        Arrays.stream(pjp.getArgs())
                .filter(o -> o instanceof HttpServletRequest)
                .map(o -> (HttpServletRequest) o)
                .findAny().ifPresent(request -> {
                    HitDto hit;
                    try {
                        hit = new HitDto("main",
                                URI.create(request.getRequestURI()),
                                InetAddress.getByName(request.getRemoteAddr()),
                                LocalDateTime.now());
                    } catch (UnknownHostException e) {
                        throw new RuntimeException(e);
                    }
                    client.hit(hit);
                });
    }

    @Override
    public int hashCode() {
        return Objects.hash(client);
    }
}
