package com.wasd.categorytreebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:properties/command.properties")
public class CategoryTreeBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryTreeBotApplication.class, args);
    }
}