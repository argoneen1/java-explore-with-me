package ru.practicum.statservice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;

@Data
@AllArgsConstructor
public class View {
    private String app;
    private URI uri;
    private long hits;
}
