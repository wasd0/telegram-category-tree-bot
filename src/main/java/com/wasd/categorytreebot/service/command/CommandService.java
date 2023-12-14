package com.wasd.categorytreebot.service.command;

import com.wasd.categorytreebot.command.Command;

public interface CommandService {
    Command getByMapping(String mapping);
}
