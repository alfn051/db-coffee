package com.dnlab.coffee.menu.common;

import com.dnlab.coffee.menu.entity.Ingredient;
import lombok.Data;

@Data
public class IngredientDTO {
    private String name;
    private long stock;
    private StockUnit stockUnit;

    public Ingredient toEntity(){
        return Ingredient.builder()
                .name(name)
                .stock(stock)
                .stockUnit(stockUnit)
                .active(true)
                .build();
    }
}
