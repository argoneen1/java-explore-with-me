package ru.practicum.ewm.base.service;

import java.util.NoSuchElementException;
import java.util.Optional;

public interface FindByIdService<T, ID> {

    Optional<T> findById(ID id);

    default T findByIdOrThrow(ID id) {
        return findById(id).orElseThrow(
                () -> new NoSuchElementException("there is no such element with id " + id));
    }
}
