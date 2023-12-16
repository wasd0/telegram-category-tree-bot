package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.category.CategoryRequest;
import com.wasd.categorytreebot.model.category.CategoryResponse;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.service.category.CategoryService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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
    public CommandResponse execute(CommandData data) {
        return switch (data.arguments().length) {
            case 1 -> addRootElement(data.arguments()[0]);
            case 2 -> addChildElement(data.arguments()[0], data.arguments()[1]);
            default ->
                    new CommandResponse(OperationStatus.FAIL, "Cannot add category with this arguments. Arguments count: " + data.arguments().length);
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
                Parameters:
                <name> - add new root element
                or
                <parentName> <name> - add element to parent
                """;
    }

    private CommandResponse addRootElement(String name) {
        try {
            CategoryResponse categoryResponse = categoryService.create(new CategoryRequest(name, null));
            return new CommandResponse(OperationStatus.SUCCESS, String.format("Added new category '%s'", categoryResponse.name()));
        } catch (EntityNotFoundException | EntityExistsException e) {
            return new CommandResponse(OperationStatus.FAIL, e.getMessage());
        }
    }

    private CommandResponse addChildElement(String parentName, String name) {
        try {
            CategoryResponse categoryResponse = categoryService.create(new CategoryRequest(name, parentName));
            return new CommandResponse(OperationStatus.SUCCESS, String.format("Added new category '%s'", categoryResponse.name()));
        } catch (EntityNotFoundException | EntityExistsException e) {
            return new CommandResponse(OperationStatus.FAIL, e.getMessage());
        }
    }
}
