package com.wasd.categorytreebot.repository;

import com.wasd.categorytreebot.model.persistence.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
