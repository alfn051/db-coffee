package com.dnlab.coffee.menu.repository;

import com.dnlab.coffee.menu.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}