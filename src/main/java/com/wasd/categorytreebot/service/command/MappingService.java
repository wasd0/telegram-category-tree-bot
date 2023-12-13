package com.wasd.categorytreebot.service.command;

import java.util.List;

public interface MappingService {
    void add(String mapping);

    List<String> getAllMappings();
}
