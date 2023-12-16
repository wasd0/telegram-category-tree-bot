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
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Service
@RequiredArgsConstructor
public class UpdateServiceImpl implements UpdateService {
    private final TelegramBotImpl categoryBot;
    private final CommandService commandService;
    private static final String FILE_PATH = "src/main/resources/dynamic/test.xlsx";

    @Override
    public void evaluate(Update update) {
        if (update.getMessage().hasText() && !update.getMessage().getText().isEmpty()) {
            String text = update.getMessage().getText();
            MessageResponse<?> response = commandService.execute(text, update.getMessage().getFrom().getId());

            if (response.getResponse() instanceof String textResponse) {
                MessageResponse<String> messageResponse = () -> textResponse;
                sendTextResponse(update, messageResponse);
            } else if (response.getResponse() instanceof Document document) {
                sendDocumentResponse(update, document);
            }

        } else {
            sendTextResponse(update, new CommandNotFoundResponse());
        }
    }

    private void sendTextResponse(Update update, MessageResponse<String> response) {
        try {
            categoryBot.execute(SendMessageUtils.sendMessage(update, response.getResponse()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendDocumentResponse(Update update, Document document) {
        try {
            GetFile getFile = new GetFile();
            getFile.setFileId(document.getFileId());
            InputFile inputFile = new InputFile(new File(FILE_PATH));
            SendDocument sendDocument = SendMessageUtils.sendMessage(update, inputFile);
            categoryBot.execute(sendDocument);

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void subscribeToBot() {
        categoryBot.setUpdateAction(this::evaluate);
    }
}
