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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::mapCategoryToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CategoryResponse create(CategoryRequest request) throws EntityExistsException, EntityNotFoundException {
        if (categoryRepository.findByName(request.name()).isPresent()) {
            throw new EntityExistsException(String.format("Category with name '%s' already exists.",
                    request.name()));
        }

        Category category = mapRequestToCategory(request);
        categoryRepository.save(category);

        return mapCategoryToResponse(category);
    }

    @Transactional
    @Override
    public void remove(String name) throws EntityNotFoundException {
        Category category = tryFindByName(name);
        categoryRepository.delete(category);
    }

    private Category mapRequestToCategory(CategoryRequest request) throws EntityNotFoundException {
        Category parent = null;
        if (request.parentName() != null && !request.parentName().isBlank()) {
            parent = tryFindByName(request.parentName());
        }

        Category category = new Category();
        category.setName(request.name());
        category.setParent(parent);

        return category;
    }

    private Category tryFindByName(String name) throws EntityNotFoundException {
        return categoryRepository.findByName(name).orElseThrow(()
                -> new EntityNotFoundException(String.format("Category with name '%s' not found", name)));
    }

    private CategoryResponse mapCategoryToResponse(Category category) {
        String parentName = category.getParent() != null ? category.getParent().getName() : null;
        boolean hasChildren = hasChildren(category);
        List<String> children = hasChildren ? new ArrayList<>() : null;

        if (hasChildren) {
            for (Category child : category.getChildren()) {
                children.add(child.getName());
            }
        }

        return new CategoryResponse(category.getName(), parentName, children);
    }

    private boolean hasChildren(Category category) {
        return category.getChildren() != null && !category.getChildren().isEmpty();
    }
}
