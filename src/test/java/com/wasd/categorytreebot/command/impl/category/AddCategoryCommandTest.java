package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.model.category.CategoryResponse;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.service.category.CategoryService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddCategoryCommandTest {

    @InjectMocks
    AddCategoryCommand addCategoryCommand;
    @Mock
    CategoryService categoryService;

    @Test
    void addRoot_whenCategoryExists_fail() {
        CommandData data = new CommandData("", "test");
        when(categoryService.create(any())).thenThrow(EntityExistsException.class);
        CommandResponse response = addCategoryCommand.execute(data);
        Assertions.assertEquals(response.status(), OperationStatus.FAIL);
    }

    @Test
    void addRoot_whenNameNotExists_success() {
        CommandData data = new CommandData("", "test");
        when(categoryService.create(any())).thenReturn(new CategoryResponse("test", null, null));
        CommandResponse response = addCategoryCommand.execute(data);
        Assertions.assertEquals(response.status(), OperationStatus.SUCCESS);
    }

    @Test
    void addChild_whenParentNameIncorrect_fail() {
        CommandData data = new CommandData("", "test", "parent");
        when(categoryService.create(any())).thenThrow(EntityNotFoundException.class);
        CommandResponse response = addCategoryCommand.execute(data);
        Assertions.assertEquals(response.status(), OperationStatus.FAIL);
    }

    @Test
    void addChild_whenParentNameCorrect_success() {
        CommandData data = new CommandData("", "test", "parent");
        when(categoryService.create(any())).thenReturn(new CategoryResponse("test", "parent", null));
        CommandResponse response = addCategoryCommand.execute(data);
        Assertions.assertEquals(response.status(), OperationStatus.SUCCESS);
    }
    
    @Test
    void add_withoutArguments_fail() {
        CommandData data = new CommandData("");
        CommandResponse response = addCategoryCommand.execute(data);
        Assertions.assertEquals(response.status(), OperationStatus.FAIL);
    }
}