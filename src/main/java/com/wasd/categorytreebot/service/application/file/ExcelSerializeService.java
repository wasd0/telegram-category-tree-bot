package com.wasd.categorytreebot.service.application.file;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExcelSerializeService {
    File serialize(String filePath, Map<Integer, List<String>> content) throws IOException, NullPointerException;
    Map<Integer, List<String>> deserialize(String filePath) throws IOException, NullPointerException;
}
