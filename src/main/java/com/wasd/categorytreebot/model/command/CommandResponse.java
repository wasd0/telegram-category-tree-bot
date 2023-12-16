package com.wasd.categorytreebot.model.command;

import com.wasd.categorytreebot.model.message.MessageResponse;

public record CommandResponse(OperationStatus status, String message) implements MessageResponse {
    
    @Override
    public String getMessage() {
        return message;
    }
}
