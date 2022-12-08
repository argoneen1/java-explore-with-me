package ru.practicum.statservice.model;

import lombok.*;
import ru.practicum.ewm.model.Base;
import ru.practicum.statservice.hibernate.URIAttributeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.net.URI;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hit")
public class Hit extends Base {

    @Column
    private String app;

    @Column(columnDefinition = "varchar(1024)")
    @Convert(converter = URIAttributeConverter.class)
    private URI uri;

    @Column(columnDefinition = "varchar(32)")
    private String ip;

}
