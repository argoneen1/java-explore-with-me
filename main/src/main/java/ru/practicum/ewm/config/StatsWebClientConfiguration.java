package ru.practicum.ewm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableSpringConfigured
public class StatsWebClientConfiguration {

    @Bean(name = "statsWebClient")
    public WebClient getStatsWebClient() {
        return WebClient.create("http://localhost:9090");
    }
}
