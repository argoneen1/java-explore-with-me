package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.UniqueException;
import ru.practicum.ewm.user.UserRepository;
import ru.practicum.ewm.user.dto.UserInsertDto;
import ru.practicum.ewm.user.dto.UserMapper;
import ru.practicum.ewm.user.model.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public List<User> findAll(List<Long> ids, Pageable pageable) {
        return ids.isEmpty() ?
                repository.findAll(pageable).getContent() :
                repository.findAllById(ids);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }


    @Override
    public User create(UserInsertDto elem) {
        try {
            return repository.save(mapper.toEntity(elem));
        } catch (DataIntegrityViolationException e) {
            throw new UniqueException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(UserInsertDto elem) {
        User updated = findByIdOrThrow(elem.getId());
        mapper.update(elem, updated);
        try {
            return repository.save(updated);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
