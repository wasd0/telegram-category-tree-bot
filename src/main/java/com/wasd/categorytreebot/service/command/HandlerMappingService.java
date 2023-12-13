package com.wasd.categorytreebot.service.command;

import java.util.List;

public interface HandlerMappingService {
    void add(String mapping);

    List<String> getAllMappings();
}
