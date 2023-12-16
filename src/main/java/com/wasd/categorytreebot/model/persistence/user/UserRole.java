package com.wasd.categorytreebot.model.persistence.user;

import com.wasd.categorytreebot.model.role.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "user_id", nullable = false, unique = true)
    private long userId;
    
    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Role role;
}
