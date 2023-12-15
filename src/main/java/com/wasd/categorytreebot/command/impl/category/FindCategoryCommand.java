package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.category.CategoryResponse;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.message.MessageResponse;
import com.wasd.categorytreebot.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindCategoryCommand implements Command {
    private final CategoryService categoryService;
    @Value("${command.findCategory.mapping}")
    private String mapping;

    @Override
    public MessageResponse execute(CommandData data) {
        List<CategoryResponse> roots = categoryService.findAllRoots();

        if (!roots.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Categories:\n");

            for (CategoryResponse root : roots) {
                stringBuilder.append("\n");
                stringBuilder.append(root.name());

                if (root.children() != null && !root.children().isEmpty()) {
                    for (String child : root.children()) {
                        stringBuilder.append(String.format(" -> %s ", child));
                    }
                }

                stringBuilder.append("\n");
            }

            return stringBuilder::toString;
        }

        return () -> "Categories not found";
    }


    @Override
    public String getMapping() {
        return mapping;
    }

    @Override
    public String getDescription() {
        return "Shows all categories in a structured way ";
    }
}
