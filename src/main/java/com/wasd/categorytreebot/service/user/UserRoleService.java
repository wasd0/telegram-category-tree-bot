package com.wasd.categorytreebot.service.user;

import com.wasd.categorytreebot.model.role.Role;
import jakarta.persistence.EntityNotFoundException;

public interface UserRoleService {
    Role getByUserId(long userId) throws EntityNotFoundException;
}
