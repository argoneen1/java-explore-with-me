package ru.practicum.ewm.model;

import lombok.*;
import org.hibernate.annotations.TypeDef;
import ru.practicum.ewm.URIAttributeConverter;
import ru.practicum.ewm.hib_types.PgInetType;

import javax.persistence.*;
import java.net.InetAddress;
import java.net.URI;
import java.time.LocalDateTime;

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
public class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String app;

    @Column(columnDefinition = "varchar(1024)")
    @Convert(converter = URIAttributeConverter.class)
    private URI uri;

    @Column(columnDefinition = "inet")
    private InetAddress ip;

    @Column
    private LocalDateTime timestamp;

}
