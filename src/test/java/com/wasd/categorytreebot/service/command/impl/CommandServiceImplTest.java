package com.wasd.categorytreebot.service.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CommandServiceImplTest {
    
    @InjectMocks
    CommandServiceImpl commandService;
    @Spy
    List<Command> commands = List.of(new TestCommand());
    
    @Test
    void getByMapping_withWrongMapping_returnsEmpty() {
        Optional<Command> command = commandService.getByMapping("");
        Assertions.assertTrue(command.isEmpty());
    }
    
    @Test
    void getByMapping_withCorrectMapping_returnsCommand() {
        Optional<Command> command = commandService.getByMapping("/test");
        Assertions.assertTrue(command.isPresent());
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