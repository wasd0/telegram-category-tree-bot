package com.wasd.categorytreebot.service.bot.impl;

import com.wasd.categorytreebot.bot.TelegramBotImpl;
import com.wasd.categorytreebot.model.message.MessageResponse;
import com.wasd.categorytreebot.service.bot.UpdateService;
import com.wasd.categorytreebot.service.command.CommandService;
import com.wasd.categorytreebot.utils.telegram.SendMessageUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class UpdateServiceImpl implements UpdateService {
    private final TelegramBotImpl categoryBot;
    private final CommandService commandService;
    
    @Override
    public void evaluate(Update update) {
        if (!update.getMessage().hasText() || update.getMessage().getText().isEmpty()) {
            return;
        }

        String text = update.getMessage().getText();
        MessageResponse response = commandService.execute(text, update.getMessage().getUserShared().getUserId());
        
        sendResponse(update, response);
    }

    private void sendResponse(Update update, MessageResponse response) {
        try {
            categoryBot.execute(SendMessageUtils.sendMessage(update, response.getMessage()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void subscribeToBot() {
        categoryBot.setUpdateAction(this::evaluate);
    }
}
