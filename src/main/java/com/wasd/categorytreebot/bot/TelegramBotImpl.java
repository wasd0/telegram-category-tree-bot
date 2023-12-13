package com.wasd.categorytreebot.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Consumer;

public class TelegramBotImpl extends TelegramLongPollingBot {
    private final String username;
    private Consumer<? super Update> updateAction;
    
    public TelegramBotImpl(String token, String username) {
        super(new DefaultBotOptions(), token);
        this.username = username;
    }
    
    public void setUpdateAction(Consumer<? super Update> updateHandler) {
        updateAction = updateHandler;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        if (updateAction != null && update != null) {
            updateAction.accept(update);
        }
    }
    
    @Override
    public String getBotUsername() {
        return username;
    }
} 
