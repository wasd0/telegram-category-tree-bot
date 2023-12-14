package com.wasd.categorytreebot.service.bot.impl;

import com.wasd.categorytreebot.bot.TelegramBotImpl;
import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.response.MessageResponse;
import com.wasd.categorytreebot.model.response.impl.CommandNotFoundResponse;
import com.wasd.categorytreebot.service.bot.UpdateService;
import com.wasd.categorytreebot.service.command.CommandService;
import com.wasd.categorytreebot.utils.command.CommandUtils;
import com.wasd.categorytreebot.utils.telegram.SendMessageUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class UpdateServiceImpl implements UpdateService {
    private static final String COMMAND_PREFIX = "/";
    private final TelegramBotImpl categoryBot;
    private final CommandService commandService;

    public UpdateServiceImpl(TelegramBotImpl categoryBot, CommandService commandService) {
        this.categoryBot = categoryBot;
        this.commandService = commandService;
    }

    @Override
    public void evaluate(Update update) {
        if (!update.getMessage().hasText() || update.getMessage().getText().isEmpty()) {
            return;
        }

        String text = update.getMessage().getText();
        MessageResponse response = new CommandNotFoundResponse();

        if (!text.startsWith(COMMAND_PREFIX)) {
            sendResponse(update, response);
            return;
        }

        CommandData commandData = CommandUtils.getCommandData(text);
        Command command = commandService.getByMapping(commandData.mapping());

        sendResponse(update, command.execute(commandData));
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
