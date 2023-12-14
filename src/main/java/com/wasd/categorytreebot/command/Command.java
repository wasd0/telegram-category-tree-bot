package com.wasd.categorytreebot.command;

import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.message.MessageResponse;

public interface Command {
    MessageResponse execute(CommandData data);
    String getMapping();
    String getDescription();
}
