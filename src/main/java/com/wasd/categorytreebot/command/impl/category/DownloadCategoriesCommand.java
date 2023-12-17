package com.wasd.categorytreebot.command.impl.category;

import com.wasd.categorytreebot.command.Command;
import com.wasd.categorytreebot.model.category.CategoryResponse;
import com.wasd.categorytreebot.model.command.CommandData;
import com.wasd.categorytreebot.model.command.CommandResponse;
import com.wasd.categorytreebot.model.command.OperationStatus;
import com.wasd.categorytreebot.model.role.Role;
import com.wasd.categorytreebot.service.application.file.ExcelSerializeService;
import com.wasd.categorytreebot.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DownloadCategoriesCommand implements Command {
    @Value("${command.downloadCategory.mapping}")
    private String mapping;
    @Value("${dynamicResourcesPath}")
    private String excelDirectoryPath;

    private final CategoryService categoryService;
    private final ExcelSerializeService excelSerializeService;

    @Override
    public CommandResponse<File> execute(CommandData data) {
        List<CategoryResponse> responses = categoryService.findAll();

        if (!responses.isEmpty()) {
            try {
                String filePath = getFilePath(data.userId());
                Map<Integer, List<String>> content = fillContent(responses);
                File file = excelSerializeService.serialize(filePath, content);

                return new CommandResponse<>(OperationStatus.SUCCESS, file);
            } catch (NullPointerException | IOException e) {
                return new CommandResponse<>(OperationStatus.FAIL, null);
            }
        }

        return new CommandResponse<>(OperationStatus.FAIL, null);
    }

    private Map<Integer, List<String>> fillContent(List<CategoryResponse> responses) {
        Map<Integer, List<String>> categoryTreeMap = new HashMap<>();
        int row = 0;

        for (CategoryResponse response : responses) {
            List<String> categories = new ArrayList<>();
            categories.add(response.getName());
            if (response.getChildren() != null) {
                categories.addAll(response.getChildren().stream()
                        .map(CategoryResponse::getName)
                        .toList());
            }
            categoryTreeMap.put(row++, categories);
        }

        return categoryTreeMap;
    }

    @Override
    public String getMapping() {
        return mapping;
    }

    @Override
    public String getDescription() {
        return "Downloads all categories in excel";
    }

    @Override
    public Role getAccessRole() {
        return Role.USER;
    }

    private String getFilePath(long userId) {
        return excelDirectoryPath +
                userId +
                ".xlsx";
    }
}
