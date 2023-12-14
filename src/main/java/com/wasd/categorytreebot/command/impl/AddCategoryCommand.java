package com.wasd.categorytreebot.command.impl;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.response.MessageResponse;
import org.springframework.stereotype.Component;

@Component
public class AddCategoryCommand implements Command {
    @Override
    public MessageResponse execute(CommandData data) {
        return switch (data.arguments().length) {
            case 1 -> addRootElement(data.arguments()[0]);
            case 2 -> addChildElement(data.arguments()[0], data.arguments()[1]);
            default -> () -> "Cannot add category with this arguments. Arguments count: " + data.arguments().length;
        };
    }

    @Override
    public String getMapping() {
        return "/addElement";
    }

    @Override
    public String getDescription() {
        return """
                Add new root or child element.
                Example:
                /addElement <name> - add new root element
                /addElement <parentName> <name> - add element to parent
                """;
    }
    
    private MessageResponse addRootElement(String name) {
        return () -> String.format("Category '%s' successful added", name);
    }
    
    private MessageResponse addChildElement(String parentName, String name) {
        return () -> String.format("Category '%s' successful added to parent category '%s'", name,
                parentName);
    }
}
