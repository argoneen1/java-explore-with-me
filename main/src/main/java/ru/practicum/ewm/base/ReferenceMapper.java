package ru.practicum.ewm.base;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.NoSuchElementException;

@Component
public class ReferenceMapper {

    @PersistenceContext
    private EntityManager entityManager;

    @ObjectFactory
    public <T> T map(@NonNull final Long id, @TargetType Class<T> type) {
        T entity = entityManager.getReference(type, id);
        if (entity == null) {
            throw new NoSuchElementException("there is no such " + type.getSimpleName() + " with id " + id);
        }
        return entity;
    }
}

