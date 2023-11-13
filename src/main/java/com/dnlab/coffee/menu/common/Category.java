package com.dnlab.coffee.menu.common;

import lombok.Getter;

@Getter
public enum Category {
    ALL("전체"),COFFEE("커피"), TEA("차"), BEVERAGE("음료"), BAKERY("빵"), GOODS("상품"), SET("세트");

    private final String displayName;
    Category(String displayName) {
        this.displayName = displayName;
    }
}
