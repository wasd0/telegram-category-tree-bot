package com.wasd.categorytreebot.service.command;

import com.wasd.categorytreebot.model.response.MessageResponse;

public interface CommandService {
    MessageResponse<?> execute(String textCommand, long userId);
}
