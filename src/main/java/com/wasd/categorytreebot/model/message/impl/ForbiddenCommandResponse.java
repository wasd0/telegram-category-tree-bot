package com.wasd.categorytreebot.model.message.impl;

import com.wasd.categorytreebot.model.message.MessageResponse;

public class ForbiddenCommandResponse implements MessageResponse {
    @Override
    public String getMessage() {
        return """
                Forbidden command.
                Please, send '/help'
                for display all available commands.""";
    }
}
