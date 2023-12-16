package com.wasd.categorytreebot.model.role;

import lombok.Getter;

@Getter
public enum Role {
    USER(1), ADMIN(2), SUPER_ADMIN(3);

    private final int value;

    Role(int value) {
        this.value = value;
    }

}
