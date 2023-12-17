package com.wasd.categorytreebot.model.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryResponse {
    private String name;
    private String parentName;
    private List<CategoryResponse> children;
}