package ru.practicum.ewm.category.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.base.model.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_categories")
public class Category extends Base {

    @Column(unique = true)
    private String name;

}
