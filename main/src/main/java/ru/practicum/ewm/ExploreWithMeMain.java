package ru.practicum.ewm;

import org.springframework.beans.factory.annotation.Value;
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
    public StatsClient getStatsWebClient(
            @Value("${stats-server.address:http://localhost:9090}")
            String url) {
        System.out.println(url);
        return new StatsClientImpl(WebClient.builder().baseUrl(url));
    }
}
