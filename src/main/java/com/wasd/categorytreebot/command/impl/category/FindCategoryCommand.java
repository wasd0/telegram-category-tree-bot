package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.category.CategoryResponse;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.model.role.Role;
import com.wasd.categorytreebot.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FindCategoryCommand implements Command {
    private final CategoryService categoryService;
    @Value("${command.findCategory.mapping}")
    private String mapping;

    @Override
    public CommandResponse<?> execute(CommandData data) {
        List<CategoryResponse> categories = categoryService.findAll();

        if (!categories.isEmpty()) {
            
            String result = printCategoryTree(categories);

            return new CommandResponse<>(OperationStatus.SUCCESS, result);
        }

        return new CommandResponse<>(OperationStatus.FAIL, "Categories not found");
    }

    private String printCategoryTree(List<CategoryResponse> categories) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Categories:\n");
        Set<CategoryResponse> printed = new HashSet<>();
        
        for (CategoryResponse category : categories) {
            ArrayDeque<Pair<CategoryResponse, Integer>> categoryQueue = new ArrayDeque<>();
            categoryQueue.addLast(Pair.of(category, 0));

            while (!categoryQueue.isEmpty()) {
                Pair<CategoryResponse, Integer> pair = categoryQueue.pollLast();
                CategoryResponse currentCategory = pair.getFirst();
                int currentLevel = pair.getSecond();

                if (!printed.add(currentCategory)) {
                    continue;
                }

                stringBuilder.append("  ".repeat(Math.max(0, currentLevel)));
                stringBuilder.append("-> ");
                stringBuilder.append(currentCategory.getName());

                List<CategoryResponse> children = currentCategory.getChildren();
                if (children != null && !children.isEmpty()) {
                    stringBuilder.append("\n");
                    for (CategoryResponse child : children) {
                        categoryQueue.addLast(Pair.of(child, currentLevel + 1));
                    }
                } else {
                    stringBuilder.append("\n");
                }
            }
        }
        
        return stringBuilder.toString();
    }


    @Override
    public String getMapping() {
        return mapping;
    }

    @Override
    public String getDescription() {
        return "Shows all categories in a structured way ";
    }

    @Override
    public Role getAccessRole() {
        return Role.USER;
    }
}
