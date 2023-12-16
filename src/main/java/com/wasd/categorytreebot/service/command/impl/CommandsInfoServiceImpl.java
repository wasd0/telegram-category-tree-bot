package com.wasd.categorytreebot.service.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.role.Role;
import com.wasd.categorytreebot.service.command.CommandsInfoService;
import com.wasd.categorytreebot.service.user.UserRoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandsInfoServiceImpl implements CommandsInfoService {
    private final List<Command> commands = new ArrayList<>();
    private final UserRoleService userRoleService;

    public CommandsInfoServiceImpl(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public void init(List<Command> commands) {
        this.commands.addAll(commands);
    }

    @Override
    public List<String> getUserCommandsInfo(long userId) {
        Role role;

        try {
            role = userRoleService.getByUserId(userId);
        } catch (EntityNotFoundException e) {
            role = Role.USER;
        }

        final int userRoleValue = role.getValue();

        return commands.stream()
                .filter(command -> userRoleValue >= command.getAccessRole().getValue())
                .map(command -> String.format("\n%s\n%s\n\n", command.getMapping(),
                        command.getDescription()))
                .collect(Collectors.toList());
    }
}