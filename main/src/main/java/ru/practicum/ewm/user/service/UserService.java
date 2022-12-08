package ru.practicum.ewm.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.service.FindByIdService;
import ru.practicum.ewm.user.dto.UserInsertDto;
import ru.practicum.ewm.user.model.User;

import java.util.List;

public interface UserService extends FindByIdService<User, Long> {

    List<User> findAll(List<Long> ids, Pageable pageable);

    User create(UserInsertDto elem);

    User update(UserInsertDto elem);

    void delete(Long id);
}
