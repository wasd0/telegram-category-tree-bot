package com.wasd.categorytreebot.service.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.message.MessageResponse;
import com.wasd.categorytreebot.model.message.impl.CommandNotFoundResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CommandServiceImplTest {
    
    @InjectMocks
    CommandServiceImpl commandService;
    @Spy
    List<Command> commands = List.of(new TestCommand());
    
    @Test
    void execute_withWrongMapping_returnsCommandNotFoundResponse() {
        MessageResponse response = commandService.execute("");
        Assertions.assertTrue(response instanceof CommandNotFoundResponse);
    }
    
    @Test
    void getByMapping_withCorrectMapping_returnsMessageResponse() {
        MessageResponse response = commandService.execute("/test");
        Assertions.assertFalse(response instanceof CommandNotFoundResponse);
    }
    
    static class TestCommand implements Command {

        @Override
        public CommandResponse execute(CommandData data) {
            return null;
        }

        @Override
        public String getMapping() {
            return "/test";
        }

        @Override
        public String getDescription() {
            return null;
        }
    }
}