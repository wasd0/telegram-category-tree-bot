package com.wasd.categorytreebot.service.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.model.message.MessageResponse;
import com.wasd.categorytreebot.model.message.impl.CommandNotFoundResponse;
import com.wasd.categorytreebot.model.message.impl.ForbiddenCommandResponse;
import com.wasd.categorytreebot.model.role.Role;
import com.wasd.categorytreebot.service.user.impl.UserRoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandServiceImplTest {
    
    @InjectMocks
    CommandServiceImpl commandService;
    @Spy
    List<Command> commands = List.of(new TestCommand());
    @Mock
    UserRoleServiceImpl userRoleService;
    
    @Test
    void execute_withWrongMapping_returnsCommandNotFoundResponse() {
        MessageResponse response = commandService.execute("", 123);
        Assertions.assertTrue(response instanceof CommandNotFoundResponse);
    }
    
    @Test
    void execute_withCorrectMapping_returnsCommandResponse() {
        when(userRoleService.getByUserId(123)).thenReturn(Role.SUPER_ADMIN);
        MessageResponse response = commandService.execute("/test", 123);
        Assertions.assertTrue(response instanceof CommandResponse);
    }
    
    @Test
    void execute_withWeakUserRole_returnsForbiddenCommandResponse() {
        when(userRoleService.getByUserId(123)).thenReturn(Role.USER);
        MessageResponse response = commandService.execute("/test", 123);
        Assertions.assertTrue(response instanceof ForbiddenCommandResponse);
    }
    
    static class TestCommand implements Command {

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
            return Role.ADMIN;
        }
    }
}