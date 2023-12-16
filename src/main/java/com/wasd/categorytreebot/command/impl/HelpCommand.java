package com.wasd.categorytreebot.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.model.role.Role;
import com.wasd.categorytreebot.service.command.CommandsInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommand implements Command {
    private final CommandsInfoService commandsInfoService;

    @Value("${command.help.mapping}")
    private String mapping;

    @Override
    public CommandResponse execute(CommandData data) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Available commands:\n");
        stringBuilder.append(Strings.repeat("_", 40));

        commandsInfoService.getInfo().forEach(stringBuilder::append);
        stringBuilder.append(Strings.repeat("_", 40));

        return new CommandResponse(OperationStatus.SUCCESS, stringBuilder.toString());
    }

    @Override
    public String getMapping() {
        return mapping;
    }

    @Override
    public String getDescription() {
        return "Show all available commands";
    }

    @Override
    public Role getAccessRole() {
        return Role.USER;
    }
}
