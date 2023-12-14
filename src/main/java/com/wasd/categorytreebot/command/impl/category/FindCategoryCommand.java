package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.category.CategoryResponse;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.message.MessageResponse;
import com.wasd.categorytreebot.service.category.CategoryService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindCategoryCommand implements Command {
    private final CategoryService categoryService;

    public FindCategoryCommand(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public MessageResponse execute(CommandData data) {
        return findAllCategories();
    }

    @Override
    public String getMapping() {
        return "/viewTree";
    }

    @Override
    public String getDescription() {
        return "Shows all categories in a structured way ";
    }

    private MessageResponse findAllCategories() {
        List<CategoryResponse> responses = categoryService.findAll();
        if (!responses.isEmpty()) {
            StringBuilder categories = new StringBuilder();
            for (CategoryResponse response : responses) {
                categories.append("> ");
                categories.append(response.name());
            }

            final String result = categories.toString();
            return () -> result;
        } else {
            return () -> "No categories for display";
        }
    }
}
