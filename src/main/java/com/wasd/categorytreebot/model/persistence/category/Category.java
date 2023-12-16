package com.wasd.categorytreebot.model.persistence.category;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "children")
@NoArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @OrderBy("parent")
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Category parent;
    
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @OrderBy("name")
    private List<Category> children;

}
