package com.wasd.categorytreebot.service.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateService {
    void evaluate(Update update);
}
