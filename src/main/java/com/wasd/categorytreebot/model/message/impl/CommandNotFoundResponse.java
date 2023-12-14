package com.wasd.categorytreebot.model.message.impl;

import com.wasd.categorytreebot.model.message.MessageResponse;

public class CommandNotFoundResponse implements MessageResponse {
    @Override
    public String getMessage() {
        return """
                Command not found.
                Please, send '/help'
                for display all available commands.""";
    }
}
