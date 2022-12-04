package ru.practicum.ewm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.statservice.StatsClient;
import ru.practicum.statservice.StatsClientImpl;

@SpringBootApplication
public class ExploreWithMeMain {
    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMeMain.class, args);
    }

    @Bean
    public StatsClient getStatsWebClient() {
        return new StatsClientImpl(WebClient.builder());
    }
}
