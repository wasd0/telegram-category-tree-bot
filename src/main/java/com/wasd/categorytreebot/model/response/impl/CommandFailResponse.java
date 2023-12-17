package com.wasd.categorytreebot.model.response.impl;

import com.wasd.categorytreebot.model.response.MessageResponse;

public class CommandFailResponse implements MessageResponse<String> {
    @Override
    public String getResponse() {
        return "Command fail. Something went wrong...";
    }
}
