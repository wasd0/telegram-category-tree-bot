package com.wasd.categorytreebot.model.response.impl;

import com.wasd.categorytreebot.model.response.MessageResponse;

public record CommandNotFoundResponse() implements MessageResponse {
    @Override
    public String getMessage() {
        return """
                Command not found.
                Please, send '/help'
                for display all available commands.""";
    }
}
