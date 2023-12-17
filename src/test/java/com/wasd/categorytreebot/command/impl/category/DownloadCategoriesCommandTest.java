package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.model.category.CategoryResponse;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.service.application.bot.impl.DownloadCategoriesCommand;
import com.wasd.categorytreebot.service.application.file.impl.XlsxSerializeService;
import com.wasd.categorytreebot.service.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DownloadCategoriesCommandTest {
    
    @InjectMocks
    DownloadCategoriesCommand downloadCategoriesCommand;
    @Mock
    CategoryService categoryService;
    @Mock
    XlsxSerializeService xlsxSerializeService;
    
    List<CategoryResponse> responses;
    
    @BeforeEach
    void setUp() {
        CategoryResponse response1 = new CategoryResponse("test1", null, null);
        CategoryResponse response2 = new CategoryResponse("test2", null, null);
        
        responses = List.of(response1, response2);
    }
    
    @Test
    void download_whenCategoriesExists_returnsSuccess() {
        when(categoryService.findAll()).thenReturn(responses);
        CommandResponse<?> response = downloadCategoriesCommand.execute(new CommandData(0, ""));
        assertEquals(response.status(), OperationStatus.SUCCESS);
    }
    
    @Test
    void download_whenCategoriesEmpty_returnsFail() {
        CommandResponse<?> response = downloadCategoriesCommand.execute(new CommandData(0, ""));
        assertEquals(response.status(), OperationStatus.FAIL);
    }
    
    @Test
    void download_whenSerializeException_returnsFail() {
        CommandResponse<?> response = downloadCategoriesCommand.execute(new CommandData(0, ""));
        assertEquals(response.status(), OperationStatus.FAIL);
    }
}