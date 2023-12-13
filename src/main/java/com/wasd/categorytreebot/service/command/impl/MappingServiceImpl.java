package com.wasd.categorytreebot.service.command.impl;

import com.wasd.categorytreebot.service.command.MappingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MappingServiceImpl implements MappingService {
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