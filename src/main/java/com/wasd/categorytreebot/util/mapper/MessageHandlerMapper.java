package com.wasd.categorytreebot.util.mapper;

import com.wasd.categorytreebot.handler.MessageHandler;

public interface MessageHandlerMapper {
    MessageHandler getByMapping(String message) throws IllegalArgumentException;
}