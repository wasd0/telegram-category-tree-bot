package com.wasd.categorytreebot.service.command;

import com.wasd.categorytreebot.command.Command;

import java.util.Optional;

public interface CommandService {
    Optional<Command> getByMapping(String mapping);
}
