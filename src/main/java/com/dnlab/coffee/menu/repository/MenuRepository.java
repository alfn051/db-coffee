package com.dnlab.coffee.menu.repository;

import com.dnlab.coffee.menu.common.Category;
import com.dnlab.coffee.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    public List<Menu> findAllByActive(boolean active);

    public List<Menu> findAllByNameContainingAndActive(String name, boolean active);

    public List<Menu> findAllByCategoryAndActive(Category category, boolean active);
    public List<Menu> findAllByNameContainingAndCategoryAndActive(String name, Category category, boolean active);

    public boolean existsByNameAndActive(String name, boolean active);
}