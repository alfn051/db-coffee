package com.dnlab.coffee.menu.repository;

import com.dnlab.coffee.menu.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    public Optional<Ingredient> findByName(String name);

    public List<Ingredient> findAllByActive(boolean active);

    public boolean existsByNameAndActive(String name, boolean active);
}