package com.wasd.categorytreebot.service.command;

import com.wasd.categorytreebot.command.Command;

import java.util.List;

public interface CommandsInfoService {
    void init(List<Command> commands);

    List<String> getUserCommandsInfo(long userId);
}
