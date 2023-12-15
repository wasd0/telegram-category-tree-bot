package com.wasd.categorytreebot.model.category;

import java.util.List;

public record CategoryResponse(String name, String parentName, List<String> children) {
}