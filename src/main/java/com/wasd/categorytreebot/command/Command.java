package com.wasd.categorytreebot.command;

import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.role.Role;

public interface Command {
    CommandResponse<?> execute(CommandData data);
    String getMapping();
    String getDescription();
    Role getAccessRole();
}
