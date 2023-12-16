package com.wasd.categorytreebot.model.response.impl;

import com.wasd.categorytreebot.model.response.MessageResponse;

public class CommandNotFoundResponse implements MessageResponse<String> {
    @Override
    public String getResponse() {
        return """
                Command not found.
                Please, send '/help'
                for display all available commands.""";
    }
}

