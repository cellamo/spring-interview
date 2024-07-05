package com.iotiq.interview.service;

import com.iotiq.interview.controller.messages.CategoryRequest;
import com.iotiq.interview.domain.Category;
import com.iotiq.interview.domain.Menu;
import com.iotiq.interview.repository.CategoryRepository;
import com.iotiq.interview.repository.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category create(CategoryRequest request) {
        Menu menu = menuRepository
                .findById(request.getMenuId())
                .orElseThrow(EntityNotFoundException::new);

        Category category = new Category();
        category.setName(request.getName());
        category.setMenu(menu);

        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
