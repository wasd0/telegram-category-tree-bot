package com.wasd.categorytreebot.command.impl;

import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.service.command.impl.CommandsInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelpCommandTest {

    @InjectMocks
    HelpCommand helpCommand;
    @Mock
    CommandsInfoServiceImpl commandsInfoService;
    
    @Test
    void execute_returnsListOfCommands() {
        when(commandsInfoService.getUserCommandsInfo(123)).thenReturn(List.of("list", "of", "commands"));
        CommandResponse response = helpCommand.execute(new CommandData(123, ""));
        assertEquals(response.status(), OperationStatus.SUCCESS);
    }
}