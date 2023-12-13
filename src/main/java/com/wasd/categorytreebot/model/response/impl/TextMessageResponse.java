package com.wasd.categorytreebot.model.response.impl;

import com.wasd.categorytreebot.model.response.MessageResponse;

import java.util.Objects;

public record TextMessageResponse(String message) implements MessageResponse {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextMessageResponse that)) return false;
        return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public String toString() {
        return "TextMessageResponse{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public String getMessage() {
        return message;
    }
}
