package com.dnlab.coffee.menu.common;

import com.dnlab.coffee.menu.entity.Menu;
import lombok.Data;

@Data
public class MenuDTO {
    private String name;
    private long price;
    private boolean special;
    private boolean mainMenu;
    private Category category;
    //private List<RecipeDTO> recipes;

    public Menu toEntity(){
        return Menu.builder()
                .name(name)
                .price(price)
                .active(true)
                .special(special)
                .mainMenu(mainMenu)
                .category(category)
                .build();
    }
}
