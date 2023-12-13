package com.wasd.categorytreebot.util.mapper.impl;

import com.wasd.categorytreebot.exception.HandlerMappingExistsException;
import com.wasd.categorytreebot.handler.MessageHandler;
import com.wasd.categorytreebot.service.command.MappingService;
import com.wasd.categorytreebot.util.mapper.MessageHandlerMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageHandlerMapperImpl implements MessageHandlerMapper {
    private final MappingService commandService;
    private final List<MessageHandler> handlers;
    private Map<String, MessageHandler> handlerMap;

    public MessageHandlerMapperImpl(MappingService commandService, List<MessageHandler> handlers) {
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