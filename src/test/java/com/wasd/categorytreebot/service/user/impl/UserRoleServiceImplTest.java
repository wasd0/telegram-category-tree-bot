package com.wasd.categorytreebot.service.user.impl;

import com.wasd.categorytreebot.model.persistence.user.UserRole;
import com.wasd.categorytreebot.model.role.Role;
import com.wasd.categorytreebot.repository.UserRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {

    @InjectMocks
    UserRoleServiceImpl roleService;
    @Mock
    UserRoleRepository userRoleRepository;
    
    @Test
    void getRoleByUserId_incorrectId_throwsEntityNotFoundException() {
        when(userRoleRepository.getByUserId(123)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> roleService.getByUserId(123));
    }
    
    @Test
    void getRoleByUserId_correctId_returnsRole() {
        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        
        when(userRoleRepository.getByUserId(123)).thenReturn(Optional.of(userRole));
        Role role = roleService.getByUserId(123);
        assertEquals(role, Role.USER);
    }
}