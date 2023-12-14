package com.wasd.categorytreebot.service.command.impl;

import com.wasd.categorytreebot.service.command.CommandsInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommandsInfoServiceImpl implements CommandsInfoService {
    private final List<String> info = new ArrayList<>();

    @Override
    public void addCommandsInfo(List<String> info) {
        this.info.addAll(info);
    }

    @Override
    public List<String> getInfo() {
        return info;
    }
}