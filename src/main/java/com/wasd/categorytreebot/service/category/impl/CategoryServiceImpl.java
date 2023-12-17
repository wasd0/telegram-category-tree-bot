package com.wasd.categorytreebot.service.category.impl;

import com.wasd.categorytreebot.model.category.CategoryRequest;
import com.wasd.categorytreebot.model.category.CategoryResponse;
import com.wasd.categorytreebot.model.persistence.category.Category;
import com.wasd.categorytreebot.repository.CategoryRepository;
import com.wasd.categorytreebot.service.category.CategoryService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        ArrayDeque<Pair<Category, CategoryResponse>> deque = new ArrayDeque<>();
        Map<Category, CategoryResponse> categoryChildren = new HashMap<>();

        CategoryResponse response = new CategoryResponse(category.getName(),
                category.getParent() != null ? category.getParent().getName() : null, null);
        
        deque.push(Pair.of(category, response));

        while (!deque.isEmpty()) {
            Pair<Category, CategoryResponse> pair = deque.pollLast();
            Category currentCategory = pair.getFirst();
            CategoryResponse currentResponse = pair.getSecond();

            categoryChildren.put(currentCategory, currentResponse);

            if (hasChildren(currentCategory)) {
                List<CategoryResponse> children = new ArrayList<>();
                for (Category child : currentCategory.getChildren()) {
                    CategoryResponse childResponse = new CategoryResponse(child.getName(), currentCategory.getName(), null);
                    children.add(childResponse);
                    deque.addLast(Pair.of(child, childResponse));
                }
                currentResponse.setChildren(children);
            }
        }

        return categoryChildren.get(category);
    }

    private boolean hasChildren(Category category) {
        return category.getChildren() != null && !category.getChildren().isEmpty();
    }
}
