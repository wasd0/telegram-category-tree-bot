package com.wasd.categorytreebot.service.command.impl;

import com.wasd.categorytreebot.service.command.HandlerMappingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HandlerMappingServiceImpl implements HandlerMappingService {
    private final List<String> mappings = new ArrayList<>();

    @Override
    public void add(String mapping) {
        mappings.add(mapping);
    }

    @Override
    public List<String> getAllMappings() {
        return mappings;
    }
}