package com.wasd.categorytreebot.service.application.file.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class XlsxSerializeServiceTest {

    @InjectMocks
    XlsxSerializeService xlsxSerializeService;
    
    static final String filePath = "src/test/resources/dynamic/xlsxSerializeService_test.xlsx";

    @Test
    void serialize_whenListNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> xlsxSerializeService.serialize(filePath, null));
    }
    
    @Test
    void serialize_whenFilePathNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> xlsxSerializeService.serialize(null, new HashMap<>()));
    }

    @Test
    void serialize_whenListEmpty_returnsFile() throws IOException {
        File file = xlsxSerializeService.serialize(filePath, new HashMap<>());
        assertNotNull(file);
    }

    @Test
    void serialize_whenListNotEmpty_returnsFile() throws IOException {
        HashMap<Integer, List<String>> content = new HashMap<>();
        content.put(0, List.of(""));
        content.put(1, List.of(""));
        
        File file = xlsxSerializeService.serialize(filePath, content);
        assertNotNull(file);
    }
}