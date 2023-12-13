package com.wasd.categorytreebot.util.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public final class SendMessageUtil {
    public static SendMessage sendMessage(Update update, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messageText);
        sendMessage.setChatId(update.getMessage().getChatId());
        return sendMessage;
    }
}
