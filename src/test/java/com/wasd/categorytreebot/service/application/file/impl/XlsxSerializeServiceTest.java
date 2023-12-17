package com.wasd.categorytreebot.service.application.file.impl;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class XlsxSerializeServiceTest {

    @InjectMocks
    XlsxSerializeService xlsxSerializeService;

    static final String EMPTY_FILE_PATH = "src/test/resources/dynamic/xlsxSerializeService_test.xlsx";
    static final String CONTENT_FILE_PATH = "src/test/resources/dynamic/xlsxSerializeService_content_test.xlsx";
    static final String TXT_FILE_PATH = "src/test/resources/dynamic/xlsxSerializeService_txtFile_test.txt";

    @Test
    void serialize_whenListNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> xlsxSerializeService.serialize(EMPTY_FILE_PATH, null));
    }

    @Test
    void serialize_whenFilePathNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> xlsxSerializeService.serialize(null, new HashMap<>()));
    }

    @Test
    void serialize_whenListEmpty_returnsFile() throws IOException {
        File file = xlsxSerializeService.serialize(EMPTY_FILE_PATH, new HashMap<>());
        assertNotNull(file);
    }

    @Test
    void serialize_whenListNotEmpty_returnsFile() throws IOException {
        HashMap<Integer, List<String>> content = new HashMap<>();
        content.put(0, List.of(""));
        content.put(1, List.of(""));

        File file = xlsxSerializeService.serialize(EMPTY_FILE_PATH, content);
        assertNotNull(file);
    }

    @Test
    void deserialize_withWrongFileFormat_throwsNotOfficeXmlFileException() {
        Assertions.assertThrows(NotOfficeXmlFileException.class, () -> xlsxSerializeService.deserialize(TXT_FILE_PATH));
    }
    
    @Test
    void deserialize_withContentFile_throwsIOException() throws IOException {
        Map<Integer, List<String>> result = xlsxSerializeService.deserialize(CONTENT_FILE_PATH);
        Assertions.assertNotNull(result);
        
        for (Map.Entry<Integer, List<String>> contents : result.entrySet()) {
            assertNotNull(contents.getKey());
            assertNotNull(contents.getValue());
            assertFalse(contents.getValue().isEmpty());
        }
    }
}