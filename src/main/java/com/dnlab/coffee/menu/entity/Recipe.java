package com.dnlab.coffee.menu.entity;

import com.dnlab.coffee.config.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "recipe")
public class Recipe extends BaseEntity {
    private long requiredAmount;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
}