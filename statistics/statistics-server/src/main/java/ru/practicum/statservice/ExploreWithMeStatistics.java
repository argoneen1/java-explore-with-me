package ru.practicum.statservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ru.practicum.ewm", "ru.practicum.statservice"})
public class ExploreWithMeStatistics {
    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMeStatistics.class, args);
    }
}
