package com.wasd.categorytreebot.model.response.impl;

import com.wasd.categorytreebot.model.response.MessageResponse;

public class ForbiddenCommandResponse implements MessageResponse<String> {
    @Override
    public String getResponse() {
        return """
                Forbidden command.
                Please, send '/help'
                for display all available commands.""";
    }
}
