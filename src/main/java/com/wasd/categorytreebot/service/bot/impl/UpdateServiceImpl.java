package com.wasd.categorytreebot.service.bot.impl;

import com.wasd.categorytreebot.bot.TelegramBotImpl;
import com.wasd.categorytreebot.handler.MessageHandler;
import com.wasd.categorytreebot.model.response.MessageResponse;
import com.wasd.categorytreebot.model.response.impl.CommandNotFoundResponse;
import com.wasd.categorytreebot.service.bot.UpdateService;
import com.wasd.categorytreebot.service.mapper.MessageHandlerMapper;
import com.wasd.categorytreebot.util.telegram.SendMessageUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class UpdateServiceImpl implements UpdateService {
    private final TelegramBotImpl categoryBot;
    private final MessageHandlerMapper mapper;

    public UpdateServiceImpl(TelegramBotImpl categoryBot, MessageHandlerMapper mapper) {
        this.categoryBot = categoryBot;
        this.mapper = mapper;
    }

    @Override
    public void evaluate(Update update) {
        if (!update.getMessage().hasText() || update.getMessage().getText().isEmpty()) {
            return;
        }

        String command = update.getMessage().getText();
        MessageHandler handler = mapper.getByMapping(command);
        MessageResponse response = handler != null ? handler.execute() :
                new CommandNotFoundResponse();
        
        try {
            categoryBot.execute(SendMessageUtil.sendMessage(update, response.getMessage()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void subscribeToBot() {
        categoryBot.setUpdateAction(this::evaluate);
    }
}
