package com.wasd.categorytreebot.service.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.exception.CommandExistsException;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.message.MessageResponse;
import com.wasd.categorytreebot.model.message.impl.CommandNotFoundResponse;
import com.wasd.categorytreebot.service.command.CommandService;
import com.wasd.categorytreebot.service.command.CommandsInfoService;
import com.wasd.categorytreebot.utils.command.CommandUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommandServiceImpl implements CommandService {
    private static final String COMMAND_PREFIX = "/";
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
    public MessageResponse execute(String text) {
        if (!text.startsWith(COMMAND_PREFIX)) {
            return new CommandNotFoundResponse();
        }

        CommandData commandData = CommandUtils.getCommandData(text);
        Command command = commandMap.get(commandData.mapping());

        return command != null ? command.execute(commandData) :
                new CommandNotFoundResponse();
    }

    @PostConstruct
    private void init() {
        commandInfoService.addCommandsInfo(commandMap.values().stream()
                .map(command -> String.format("\n%s\n%s\n\n", command.getMapping(), command.getDescription()))
                .toList());
    }
}
