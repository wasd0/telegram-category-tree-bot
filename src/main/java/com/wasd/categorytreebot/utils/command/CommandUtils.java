package com.wasd.categorytreebot.utils.command;

import com.wasd.categorytreebot.model.command.CommandData;

import java.util.Arrays;
import java.util.List;

public final class CommandUtils {
    private CommandUtils() {
        
    }
    
    public static CommandData getCommandData(long userId, String text) {
        List<String> parts = Arrays.stream(text.split(" ")).filter(s -> !s.isBlank()).toList();
        String mapping = parts.get(0);
        String[] arguments = new String[parts.size() - 1];

        for (int i = 1; i < parts.size(); i++) {
            arguments[i - 1] = parts.get(i);
        }
        
        return new CommandData(userId, mapping, arguments);
    }
}
