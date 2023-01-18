package ru.practicum.ewm.category.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.service.FindByIdService;

public interface CategoryService extends FindByIdService<Category, Long> {

    Category create(CategoryDto elem);

    Category update(CategoryDto elem);

    Page<Category> findAll(Pageable pageable);

    void delete(long id);

}
