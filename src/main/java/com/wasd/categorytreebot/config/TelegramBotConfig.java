package com.wasd.categorytreebot.config;

import com.wasd.categorytreebot.bot.CategoryBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@PropertySource("classpath:properties/categoryBot.properties")
public class TelegramBotConfig {
    @Value("${categoryBot.username}")
    private String categoryBotUsername;
    @Value("${categoryBot.token}")
    private String categoryBotToken;
    
    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(categoryBot());
        return telegramBotsApi;
    }
    
    @Bean
    public CategoryBot categoryBot() {
        return new CategoryBot(categoryBotToken, categoryBotUsername);
    }
}
