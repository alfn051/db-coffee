package com.dnlab.coffee.menu.entity;

import com.dnlab.coffee.menu.common.Category;
import com.dnlab.coffee.config.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class Menu extends BaseEntity {
    @Column
    private String name;
    @Column
    private long price;
    @Column
    private boolean active;
    @Column
    private boolean special;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @OneToMany(mappedBy = "menu", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Recipe> recipes = new LinkedList<>();

}