package com.wasd.categorytreebot.service.application.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateService {
    void evaluate(Update update);
}
