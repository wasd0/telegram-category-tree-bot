package com.wasd.categorytreebot.model.command;

import com.wasd.categorytreebot.model.response.MessageResponse;

public record CommandResponse<T>(OperationStatus status, T response) implements MessageResponse<T> {
    @Override
    public T getResponse() {
        return response;
    }
}
