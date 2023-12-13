package com.wasd.categorytreebot.handler.impl;

import com.wasd.categorytreebot.handler.MessageHandler;
import com.wasd.categorytreebot.model.response.MessageResponse;
import com.wasd.categorytreebot.model.response.impl.TextMessageResponse;
import com.wasd.categorytreebot.service.command.HandlerMappingService;
import org.springframework.stereotype.Component;

@Component
public class HelpHandler implements MessageHandler {
    private final HandlerMappingService mappingService;

    public HelpHandler(HandlerMappingService commandService) {
        this.mappingService = commandService;
    }

    @Override
    public String getMapping() {
        return "/help";
    }

    @Override
    public MessageResponse execute() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Available commands:\n");
        
        mappingService.getAllMappings().forEach(mapping -> stringBuilder.append(String.format("%s\n", mapping)));

        return new TextMessageResponse(stringBuilder.toString());
    }
}
