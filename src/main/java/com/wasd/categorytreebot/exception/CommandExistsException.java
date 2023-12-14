package com.wasd.categorytreebot.exception;

public class CommandExistsException extends Exception {

    public CommandExistsException(String mapping) {
        super(String.format("Command with mapping '%s' already exists.", mapping));
    }
}
