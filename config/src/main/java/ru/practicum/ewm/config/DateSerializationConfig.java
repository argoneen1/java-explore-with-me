package ru.practicum.ewm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@Configuration
public class DateSerializationConfig {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<>() {
            @Override
            public LocalDateTime parse(String text, Locale locale) {
                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
            }

            @Override
            public String print(LocalDateTime object, Locale locale) {
                return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(object);
            }
        };
    }
}
