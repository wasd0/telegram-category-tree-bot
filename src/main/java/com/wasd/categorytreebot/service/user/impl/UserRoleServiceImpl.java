package com.wasd.categorytreebot.service.user.impl;

import com.wasd.categorytreebot.model.role.Role;
import com.wasd.categorytreebot.repository.UserRoleRepository;
import com.wasd.categorytreebot.service.user.UserRoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Override
    public Role getByUserId(long userId) throws EntityNotFoundException {
        return userRoleRepository.getByUserId(userId).orElseThrow(() -> new EntityNotFoundException(String.format("Role " +
                "by user id '%s' not found.", userId))).getRole();
    }
}
