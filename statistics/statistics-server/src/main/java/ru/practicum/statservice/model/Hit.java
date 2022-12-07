package ru.practicum.statservice.model;

import lombok.*;
import org.hibernate.annotations.TypeDef;
import ru.practicum.ewm.model.Base;
import ru.practicum.statservice.hibernate.InetAddressAttributeConverter;
import ru.practicum.statservice.hibernate.PgInetType;
import ru.practicum.statservice.hibernate.URIAttributeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.net.InetAddress;
import java.net.URI;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hit")
@TypeDef(name = "pgInet",
        typeClass = PgInetType.class,
        defaultForType = InetAddress.class
)
public class Hit extends Base {

    @Column
    private String app;

    @Column(columnDefinition = "varchar(1024)")
    @Convert(converter = URIAttributeConverter.class)
    private URI uri;

    @Column(columnDefinition = "varchar(32)")
    @Convert(converter = InetAddressAttributeConverter.class)
    private InetAddress ip;

}
