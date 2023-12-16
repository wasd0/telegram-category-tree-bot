package com.wasd.categorytreebot.service.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.model.role.Role;
import com.wasd.categorytreebot.service.user.impl.UserRoleServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandsInfoServiceImplTest {

    @InjectMocks
    CommandsInfoServiceImpl commandsInfoService;
    @Mock
    UserRoleServiceImpl userRoleService;

    @BeforeEach
    void setUp() {
        commandsInfoService.init(List.of(new TestCommand(Role.ADMIN), new TestCommand(Role.SUPER_ADMIN)));
    }
    
    @AfterEach
    void shutDown() {
        commandsInfoService.init(new ArrayList<>());
    }

    @Test
    void getUserCommandsInfo_whenHasRole_showUserCommand() {
        when(userRoleService.getByUserId(123)).thenReturn(Role.SUPER_ADMIN);
        List<String> infos = commandsInfoService.getUserCommandsInfo(123);
        Assertions.assertEquals(infos.size(), 2);
    }

    @Test
    void getUserCommandsInfo_whenNoRole_showUserCommand() {
        when(userRoleService.getByUserId(123)).thenReturn(Role.USER);
        List<String> infos = commandsInfoService.getUserCommandsInfo(123);
        Assertions.assertEquals(infos.size(), 0);
    }

    record TestCommand(Role role) implements Command {

        @Override
        public CommandResponse execute(CommandData data) {
            return new CommandResponse(OperationStatus.SUCCESS, "");
        }

        @Override
        public String getMapping() {
            return "/test";
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public Role getAccessRole() {
            return role;
        }
    }
}