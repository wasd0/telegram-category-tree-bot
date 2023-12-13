package com.wasd.categorytreebot.exception;

public class HandlerMappingExistsException extends Exception {

    public HandlerMappingExistsException(String mapping) {
        super(String.format("Handler with mapping '%s' already exists.", mapping));
    }
}
