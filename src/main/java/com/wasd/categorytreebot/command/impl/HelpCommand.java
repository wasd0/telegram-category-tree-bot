package com.wasd.categorytreebot.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.response.MessageResponse;
import com.wasd.categorytreebot.service.command.CommandsInfoService;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final CommandsInfoService mappingService;

    public HelpCommand(CommandsInfoService mappingService) {
        this.mappingService = mappingService;
    }

    @Override
    public MessageResponse execute(CommandData data) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Available commands:\n");

        mappingService.getInfo().forEach(mapping -> stringBuilder.append(String.format("%s\n", mapping)));

        return stringBuilder::toString;
    }

    @Override
    public String getMapping() {
        return "/help";
    }

    @Override
    public String getDescription() {
        return "Show all available commands";
    }
}
