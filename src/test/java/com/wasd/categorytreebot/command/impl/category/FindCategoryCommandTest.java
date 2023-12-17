package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.model.category.CategoryResponse;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.service.category.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCategoryCommandTest {
    
    @InjectMocks
    FindCategoryCommand findCategoryCommand;
    @Mock
    CategoryService categoryService;
    
    @Test
    void findCategories_categoriesNotExists_fail() {
        when(categoryService.findAll()).thenReturn(new ArrayList<>());
        CommandResponse<?> response = findCategoryCommand.execute(new CommandData(0, ""));
        Assertions.assertEquals(response.status(), OperationStatus.FAIL);
    }
    
    @Test
    void findCategories_categoriesNotExists_success() {
        when(categoryService.findAll()).thenReturn(List.of(new CategoryResponse("", "", null)));
        CommandResponse<?> response = findCategoryCommand.execute(new CommandData(0, ""));
        Assertions.assertEquals(response.status(), OperationStatus.SUCCESS);
    }
}