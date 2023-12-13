package com.wasd.categorytreebot.service.mapper.impl;

import com.wasd.categorytreebot.exception.HandlerMappingExistsException;
import com.wasd.categorytreebot.handler.MessageHandler;
import com.wasd.categorytreebot.service.command.HandlerMappingService;
import com.wasd.categorytreebot.service.mapper.MessageHandlerMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageHandlerMapperImpl implements MessageHandlerMapper {
    private final HandlerMappingService commandService;
    private final List<MessageHandler> handlers;
    private Map<String, MessageHandler> handlerMap;

    public MessageHandlerMapperImpl(HandlerMappingService commandService, List<MessageHandler> handlers) {
        this.commandService = commandService;
        this.handlers = handlers;
    }

    @Override
    public MessageHandler getByMapping(String mapping) {
        return handlerMap.get(mapping.trim());
    }

    @PostConstruct
    private Map<String, MessageHandler> fillHandlerMap() throws HandlerMappingExistsException {
        handlerMap = new HashMap<>();

        for (MessageHandler handler : handlers) {
            if (handlerMap.containsKey(handler.getMapping())) {
                throw new HandlerMappingExistsException(handler.getMapping());
            }

            String mapping = handler.getMapping();
            handlerMap.put(mapping, handler);
            commandService.add(mapping);
        }
        return handlerMap;
    }
}