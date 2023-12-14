package com.wasd.categorytreebot.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.message.MessageResponse;
import com.wasd.categorytreebot.service.command.CommandsInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommand implements Command {
    private final CommandsInfoService commandsInfoService;
    
    @Value("${command.help.mapping}")
    private String mapping;

    @Override
    public MessageResponse execute(CommandData data) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Available commands:\n");

        commandsInfoService.getInfo().forEach(mapping -> stringBuilder.append(String.format("%s\n", mapping)));

        return stringBuilder::toString;
    }

    @Override
    public String getMapping() {
        return mapping;
    }

    @Override
    public String getDescription() {
        return "Show all available commands";
    }
}
