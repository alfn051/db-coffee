package com.dnlab.coffee.menu.common;

import lombok.Getter;

@Getter
public enum StockUnit {
    GRAM("g"), MILLILITER("ml"), NUMBER("개");

    private final String displayName;
    StockUnit(String displayName) {
        this.displayName = displayName;
    }
}
