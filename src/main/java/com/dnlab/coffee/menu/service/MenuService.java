package com.dnlab.coffee.menu.service;

import com.dnlab.coffee.menu.common.Category;
import com.dnlab.coffee.menu.common.IngredientDTO;
import com.dnlab.coffee.menu.common.MenuDTO;
import com.dnlab.coffee.menu.common.RecipeDTO;
import com.dnlab.coffee.menu.entity.Ingredient;
import com.dnlab.coffee.menu.entity.Menu;
import com.dnlab.coffee.menu.entity.Recipe;
import com.dnlab.coffee.menu.repository.IngredientRepository;
import com.dnlab.coffee.menu.repository.MenuRepository;
import com.dnlab.coffee.menu.repository.RecipeRepository;
import com.dnlab.coffee.order.entity.OrderMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public Menu createMenu(MenuDTO dto) {
        Menu menu = dto.toEntity();
        return menuRepository.save(menu);
    }

    @Transactional
    public void updateMenu(long menuId, MenuDTO dto) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        if (optionalMenu.isEmpty()) return;
        Menu menu = optionalMenu.get();
        menu.setName(dto.getName());
        menu.setPrice(dto.getPrice());
        menu.setSpecial(dto.isSpecial());
        menu.setMainMenu(dto.isMainMenu());
        menu.setCategory(dto.getCategory());
    }

    @Transactional
    public void removeMenu(long menuId) {
        menuRepository.findById(menuId).ifPresent(menu -> menu.setActive(false));
    }

    public Recipe createRecipe(RecipeDTO dto) {
        Optional<Menu> optionalMenu = menuRepository.findById(dto.getMenuId());
        if (optionalMenu.isEmpty()) {
            return null;
        }
        Menu menu = optionalMenu.get();
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(dto.getIngredientId());
        if (optionalIngredient.isEmpty()) {
            return null;
        }
        Ingredient ingredient = optionalIngredient.get();

        Recipe recipe = new Recipe();
        recipe.setRequiredAmount(dto.getRequiredAmount());
        recipe.setMenu(menu);
        recipe.setIngredient(ingredient);
        return recipeRepository.save(recipe);
    }

    public Recipe getRecipe(long recipeId) {
        return recipeRepository.findById(recipeId).orElse(null);
    }

    @Transactional
    public void updateRecipe(long recipeId, long requiredAmount) {
        recipeRepository.findById(recipeId).ifPresent(recipe -> recipe.setRequiredAmount(requiredAmount));
    }

    @Transactional
    public void removeRecipe(long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    public Ingredient createIngredient(IngredientDTO dto) {
        if(ingredientRepository.existsByNameAndActive(dto.getName(), true)) return null;
        return ingredientRepository.save(dto.toEntity());
    }

    @Transactional
    public void updateIngredient(long id, IngredientDTO dto){
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (optionalIngredient.isEmpty()) return;
        Ingredient ingredient = optionalIngredient.get();
        ingredient.setName(dto.getName());
        ingredient.setStock(dto.getStock());
        ingredient.setStockUnit(dto.getStockUnit());
    }

    @Transactional
    public void removeIngredient(long id) {
        ingredientRepository.findById(id).ifPresent(ingredient -> ingredient.setActive(false));
    }

    public Ingredient getIngredient(long ingredientId) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientId);
        return optionalIngredient.orElse(null);
    }

    public Long getIngredientStock(long ingredientId) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientId);
        if (optionalIngredient.isEmpty()) return null;
        return optionalIngredient.get().getStock();
    }

    @Transactional
    public void addIngredientStock(Ingredient ingredient, long stock) {
        ingredient.setStock(ingredient.getStock() + stock);
    }

    public void subIngredientStock(OrderMenu orderMenu) {
        for (Recipe recipe : orderMenu.getMenu().getRecipes()) {
            Ingredient ingredient = recipe.getIngredient();
            ingredient.setStock(ingredient.getStock() - recipe.getRequiredAmount() * orderMenu.getQuantity());
        }
    }

    public boolean checkStockAvailable(OrderMenu orderMenu) {
        for (Recipe recipe : orderMenu.getMenu().getRecipes()) {
            if (recipe.getRequiredAmount() * orderMenu.getQuantity() > recipe.getIngredient().getStock()) {
                return false;
            }
        }
        return true;
    }

    public Menu getMenuById(long menuId) {
        return menuRepository.findById(menuId).orElse(null);
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAllByActive(true);
    }

    public List<Menu> getAllSearchedMenu(String keyword, Category category){
        if ((keyword == null || keyword.isEmpty()) && (category == null || category == Category.ALL)) {
            return menuRepository.findAllByActive(true);
        } else if(keyword!=null && (category!=null && category != Category.ALL)){
            return menuRepository.findAllByNameContainingAndCategoryAndActive(keyword, category, true);
        } else if (keyword!=null){
            return menuRepository.findAllByNameContainingAndActive(keyword, true);
        }else {

            return menuRepository.findAllByCategoryAndActive(category, true);
        }
    }

    public List<Menu> getAllRemovedMenus() {
        return menuRepository.findAllByActive(false);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAllByActive(true);
    }

}
