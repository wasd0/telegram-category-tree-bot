package com.wasd.categorytreebot.service.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.exception.CommandExistsException;
import com.wasd.categorytreebot.service.command.CommandService;
import com.wasd.categorytreebot.service.command.CommandsInfoService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommandServiceImpl implements CommandService {
    private final Map<String, Command> commandMap;
    private final CommandsInfoService commandInfoService;

    public CommandServiceImpl(List<Command> commands, CommandsInfoService commandInfoService) throws CommandExistsException {
        this.commandInfoService = commandInfoService;

        commandMap = new HashMap<>();
        for (Command command : commands) {
            if (commandMap.containsKey(command.getMapping())) {
                throw new CommandExistsException(command.getMapping());
            }

            commandMap.put(command.getMapping(), command);
        }
    }

    @Override
    public Optional<Command> getByMapping(String mapping) {
        return Optional.ofNullable(commandMap.get(mapping));
    }

    @PostConstruct
    private void init() {
        commandInfoService.addCommandsInfo(commandMap.values().stream()
                .map(command -> String.format("%s - %s\n", command.getMapping(), command.getDescription()))
                .toList());
    }
}
