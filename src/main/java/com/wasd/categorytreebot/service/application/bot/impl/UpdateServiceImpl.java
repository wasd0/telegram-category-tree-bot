package com.wasd.categorytreebot.service.application.bot.impl;

import com.wasd.categorytreebot.bot.TelegramBotImpl;
import com.wasd.categorytreebot.model.response.MessageResponse;
import com.wasd.categorytreebot.model.response.impl.CommandNotFoundResponse;
import com.wasd.categorytreebot.service.application.bot.UpdateService;
import com.wasd.categorytreebot.service.command.CommandService;
import com.wasd.categorytreebot.utils.telegram.SendMessageUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Service
@RequiredArgsConstructor
public class UpdateServiceImpl implements UpdateService {
    private final TelegramBotImpl categoryBot;
    private final CommandService commandService;

    @Override
    public void evaluate(Update update) {
        executeChatAction(update.getMessage().getChatId(), ActionType.TYPING);

        if (update.getMessage().hasText() && !update.getMessage().getText().isEmpty()) {
            String text = update.getMessage().getText();
            MessageResponse<?> response = commandService.execute(text, update.getMessage().getFrom().getId());


            if (response.getResponse() instanceof String textResponse) {
                MessageResponse<String> messageResponse = () -> textResponse;
                sendTextResponse(update, messageResponse);
            } else if (response.getResponse() instanceof File file) {
                executeChatAction(update.getMessage().getChatId(), ActionType.UPLOADDOCUMENT);
                sendDocumentResponse(update, file);
            }

        } else {
            sendTextResponse(update, new CommandNotFoundResponse());
        }
    }

    private void sendTextResponse(Update update, MessageResponse<String> response) {
        try {
            categoryBot.executeAsync(SendMessageUtils.sendMessage(update, response.getResponse()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendDocumentResponse(Update update, File file) {
        InputFile inputFile = new InputFile(file);
        SendDocument sendDocument = SendMessageUtils.sendMessage(update, inputFile);
        categoryBot.executeAsync(sendDocument).thenAccept(message -> deleteFile(file));
    }

    private static void deleteFile(File file) {
        if (!file.delete()) {
            file.deleteOnExit();
        }
    }

    private void executeChatAction(Long chatId, ActionType actionType) {
        SendChatAction sendChatAction = new SendChatAction();
        sendChatAction.setChatId(chatId);
        sendChatAction.setAction(actionType);

        try {
            categoryBot.execute(sendChatAction);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void subscribeToBot() {
        categoryBot.setUpdateAction(this::evaluate);
    }
}
