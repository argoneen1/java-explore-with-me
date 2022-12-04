package ru.practicum.statservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.InetAddress;
import java.net.URI;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HitDto {
    private String app;
    private URI uri;
    private InetAddress ip;
    private LocalDateTime timestamp;
}
