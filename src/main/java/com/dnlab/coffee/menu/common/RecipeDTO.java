package com.dnlab.coffee.menu.common;

import lombok.Data;

@Data
public class RecipeDTO {
    private long requiredAmount;
    private long ingredientId;
    private long menuId;
}
