package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.model.role.Role;
import com.wasd.categorytreebot.service.category.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveCategoryCommand implements Command {
    private final CategoryService categoryService;

    @Value("${command.removeCategory.mapping}")
    private String mapping;

    @Override
    public CommandResponse<?> execute(CommandData data) {
        if (data.arguments().length == 1) {
            try {
                String categoryName = data.arguments()[0];
                categoryService.remove(categoryName);
            } catch (EntityNotFoundException e) {
                return new CommandResponse<>(OperationStatus.FAIL, e.getMessage());
            }
            return new CommandResponse<>(OperationStatus.SUCCESS, String.format("Category '%s' removed",
                    data.arguments()[0]));
        } else {
            return new CommandResponse<>(OperationStatus.FAIL, "Category not found. Add category name to " +
                    "command for remove");
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

    @Override
    public Role getAccessRole() {
        return Role.ADMIN;
    }
}
