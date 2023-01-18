package ru.practicum.ewm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.statservice.StatsClient;
import ru.practicum.statservice.StatsClientImpl;

import static ru.practicum.ewm.config.DateSerializationConfig.DATE_TIME_FORMAT;

@Configuration
public class StatsClientConfig {

    @Bean
    public StatsClient getStatsWebClient(
            @Value("${stats-server.address:http://localhost:9090}")
            String url) {
        return new StatsClientImpl(WebClient.builder().baseUrl(url), DATE_TIME_FORMAT);
    }
}
