package com.wasd.categorytreebot.service.category.impl;

import com.wasd.categorytreebot.model.category.CategoryRequest;
import com.wasd.categorytreebot.model.persistence.category.Category;
import com.wasd.categorytreebot.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryService;
    @Mock
    CategoryRepository categoryRepository;
    
    @Test
    void create_entityExists_ThrowsEntityExistsException() {
        CategoryRequest request = new CategoryRequest("parent", null);
        when(categoryRepository.findByName("parent")).thenReturn(Optional.of(new Category()));
        assertThrows(EntityExistsException.class, () -> categoryService.create(request));
    }

    @Test
    void create_withInvalidParentName_ThrowsEntityNotFoundException() {
        CategoryRequest request = new CategoryRequest("new", "null");
        when(categoryRepository.findByName("new")).thenReturn(Optional.empty());
        when(categoryRepository.findByName("null")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> categoryService.create(request));
    }

    @Test
    void create_withNullParentName_CreateRootCategory() {
        CategoryRequest request = new CategoryRequest("new", null);
        when(categoryRepository.findByName("new")).thenReturn(Optional.empty());
        categoryService.create(request);
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    void create_withCorrectParentName_CreateChildCategory() {
        CategoryRequest request = new CategoryRequest("new", "parent");
        when(categoryRepository.findByName("new")).thenReturn(Optional.empty());
        when(categoryRepository.findByName("parent")).thenReturn(Optional.of(new Category()));
        categoryService.create(request);
        verify(categoryRepository, times(1)).save(any());
    }
    
    @Test
    void remove_ifExists_removeFromDb() {
        when(categoryRepository.findByName("new")).thenReturn(Optional.of(new Category()));
        categoryService.remove("new");
        verify(categoryRepository, times(1)).delete(any());
    }

    @Test
    void remove_ifNotExists_removeF() {
        when(categoryRepository.findByName("new")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> categoryService.remove("new"));
    }
}
