package ru.practicum.ewm.user.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.base.model.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends Base {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String email;

    public User(Long id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
    }

    public User() {
        super();
    }
}
