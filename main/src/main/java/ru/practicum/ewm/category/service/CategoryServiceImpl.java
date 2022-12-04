package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.CategoryRepository;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.exceptions.UniqueException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public Category create(CategoryDto elem) {
        try {
            return repository.save(mapper.toEntity(elem));
        } catch (DataIntegrityViolationException e) {
            throw new UniqueException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category update(CategoryDto elem) {
        Category entity = findByIdOrThrow(elem.getId());
        mapper.update(elem, entity);
        try {
            return repository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }


    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

}
