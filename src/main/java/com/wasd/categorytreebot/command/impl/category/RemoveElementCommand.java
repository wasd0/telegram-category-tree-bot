package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.message.MessageResponse;
import com.wasd.categorytreebot.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveElementCommand implements Command {
    private final CategoryService categoryService;
    
    @Value("${command.removeCategory.mapping}")
    private String mapping;

    @Override
    public MessageResponse execute(CommandData data) {
        if (data.arguments().length == 1) {
            categoryService.remove(data.arguments()[0]);
            return () -> String.format("Category '%s' removed", data.arguments()[0]);
        } else {
            return () -> "Category not found. Add category name to command for remove";
        }
    }

    @Override
    public String getMapping() {
        return mapping;
    }

    @Override
    public String getDescription() {
        return "Remove category and all him child categories\n Parameters: <name> - category name";
    }
}
