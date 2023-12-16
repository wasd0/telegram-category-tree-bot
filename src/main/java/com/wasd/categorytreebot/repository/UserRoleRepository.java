package com.wasd.categorytreebot.repository;

import com.wasd.categorytreebot.model.persistence.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> getByUserId(long id);
}
