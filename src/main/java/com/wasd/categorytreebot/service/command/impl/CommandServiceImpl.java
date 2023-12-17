package com.wasd.categorytreebot.service.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.exception.CommandExistsException;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.model.response.MessageResponse;
import com.wasd.categorytreebot.model.response.impl.CommandFailResponse;
import com.wasd.categorytreebot.model.response.impl.CommandNotFoundResponse;
import com.wasd.categorytreebot.model.response.impl.ForbiddenCommandResponse;
import com.wasd.categorytreebot.model.role.Role;
import com.wasd.categorytreebot.service.command.CommandService;
import com.wasd.categorytreebot.service.command.CommandsInfoService;
import com.wasd.categorytreebot.service.user.UserRoleService;
import com.wasd.categorytreebot.utils.command.CommandUtils;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommandServiceImpl implements CommandService {
    private static final String COMMAND_PREFIX = "/";
    private final Map<String, Command> commandMap;
    private final CommandsInfoService commandInfoService;
    private final UserRoleService userRoleService;


    public CommandServiceImpl(List<Command> commands,
                              CommandsInfoService commandInfoService,
                              UserRoleService userRoleService) throws CommandExistsException {

        this.commandInfoService = commandInfoService;
        this.userRoleService = userRoleService;

        commandMap = new HashMap<>();
        for (Command command : commands) {
            if (commandMap.containsKey(command.getMapping())) {
                throw new CommandExistsException(command.getMapping());
            }

            commandMap.put(command.getMapping(), command);
        }
    }

    @Override
    public MessageResponse<?> execute(String textCommand, long userId) {
        if (!textCommand.startsWith(COMMAND_PREFIX)) {
            return new CommandNotFoundResponse();
        }

        CommandData commandData = CommandUtils.getCommandData(userId, textCommand);
        Command command = commandMap.get(commandData.mapping());

        if (command == null) {
            return new CommandNotFoundResponse();
        }

        return tryExecuteCommand(userId, command, commandData);
    }


    private MessageResponse<?> tryExecuteCommand(long userId, Command command, CommandData commandData) {
        Role userRole;

        try {
            userRole = userRoleService.getByUserId(userId);
        } catch (EntityNotFoundException e) {
            userRole = Role.USER;
        }

        Role commandRole = command.getAccessRole();
        boolean userHasAccess = commandRole == Role.USER || userRole.getValue() >= commandRole.getValue();
        
        if (!userHasAccess) {
            return new ForbiddenCommandResponse();
        }

        CommandResponse<?> response = command.execute(commandData);
        
        return response.status().equals(OperationStatus.SUCCESS) ? response : new CommandFailResponse();
    }

    @PostConstruct
    private void init() {
        commandInfoService.init(commandMap.values().stream().toList());
    }
}
