package com.wasd.categorytreebot.service.command;

import com.wasd.categorytreebot.model.message.MessageResponse;

public interface CommandService {
    MessageResponse execute(String text);
}
