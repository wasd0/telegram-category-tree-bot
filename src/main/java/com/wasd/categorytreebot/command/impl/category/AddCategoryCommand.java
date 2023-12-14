package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.category.CategoryRequest;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.message.MessageResponse;
import com.wasd.categorytreebot.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AddCategoryCommand implements Command {
    private final CategoryService categoryService;

    @Value("${command.addCategory.mapping}")
    private String mapping;

    public AddCategoryCommand(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public MessageResponse execute(CommandData data) {
        return switch (data.arguments().length) {
            case 1 -> addRootElement(data.arguments()[0]);
            case 2 -> addChildElement(data.arguments()[0], data.arguments()[1]);
            default ->
                    () -> "Cannot add category with this arguments. Arguments count: " + data.arguments().length;
        };
    }

    @Override
    public String getMapping() {
        return mapping;
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
        return () -> categoryService.create(new CategoryRequest(name, null)).name();
    }

    private MessageResponse addChildElement(String parentName, String name) {
        return () -> categoryService.create(new CategoryRequest(name, parentName)).name();
    }
}
