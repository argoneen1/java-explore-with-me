package ru.practicum.ewm.event.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Location {
    private double lon;
    private double lat;
}
