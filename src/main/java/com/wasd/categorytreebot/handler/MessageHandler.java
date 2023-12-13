package com.wasd.categorytreebot.handler;

import com.wasd.categorytreebot.model.response.MessageResponse;

public interface MessageHandler {
    String getMapping();

    MessageResponse execute();
}
