package com.wasd.categorytreebot.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CategoryBot extends TelegramLongPollingBot {
    private final String username;
    
    public CategoryBot(String token, String username) {
        super(token);
        this.username = username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        //TODO: Implement logic
    }

    @Override
    public String getBotUsername() {
        return username;
    }
} 
