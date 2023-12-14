package com.wasd.categorytreebot.service.category.impl;

import com.wasd.categorytreebot.model.category.CategoryRequest;
import com.wasd.categorytreebot.model.category.CategoryResponse;
import com.wasd.categorytreebot.model.persistence.category.Category;
import com.wasd.categorytreebot.repository.CategoryRepository;
import com.wasd.categorytreebot.service.category.CategoryService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::mapCategoryToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.findByName(request.name()).isPresent()) {
            throw new EntityExistsException(String.format("Category with name '%s' already exists.",
                    request.name()));
        }

        Category category = mapRequestToCategory(request);
        categoryRepository.save(category);

        return mapCategoryToResponse(category);
    }

    private Category mapRequestToCategory(CategoryRequest request) {
        Category parent = null;
        if (request.parentName() != null && !request.parentName().isBlank()) {
            parent =
                    categoryRepository.findByName(request.parentName()).orElseThrow(()
                            -> new EntityNotFoundException(String.format("Parent category with " 
                            + "name '%s' not found", request.parentName())));
        }

        Category category = new Category();
        category.setName(request.name());
        category.setParent(parent);

        return category;
    }

    private CategoryResponse mapCategoryToResponse(Category category) {
        String parentName = category.getParent() != null ? category.getParent().getName() : null;
        return new CategoryResponse(category.getName(), parentName);
    }
}
