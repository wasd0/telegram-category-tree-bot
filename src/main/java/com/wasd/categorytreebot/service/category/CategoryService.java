package com.wasd.categorytreebot.service.category;

import com.wasd.categorytreebot.model.category.CategoryRequest;
import com.wasd.categorytreebot.model.category.CategoryResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAllRoots();
    CategoryResponse create(CategoryRequest request) throws EntityExistsException, EntityNotFoundException;
    void remove(String name) throws EntityNotFoundException;
}
