package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.service.category.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class RemoveCategoryCommandTest {

    @InjectMocks
    RemoveCategoryCommand removeCategoryCommand;
    @Mock
    CategoryService categoryService;
    
    @Test 
    void remove_withoutArguments_fail() {
        CommandResponse response = removeCategoryCommand.execute(new CommandData(""));
        Assertions.assertEquals(response.status(), OperationStatus.FAIL);
    }
    
    @Test
    void remove_withBadArgument_fail() {
        doThrow(EntityNotFoundException.class).when(categoryService).remove("test");
        CommandResponse response = removeCategoryCommand.execute(new CommandData("", "test"));
        Assertions.assertEquals(response.status(), OperationStatus.FAIL);
    }
    
    @Test
    void remove_withCorrectArgument_success() {
        doNothing().when(categoryService).remove("test");
        CommandResponse response = removeCategoryCommand.execute(new CommandData("", "test"));
        Assertions.assertEquals(response.status(), OperationStatus.SUCCESS);
    }
}