package com.dnlab.coffee.order.common;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CREDIT_CARD("신용카드"), CASH("현금"), CHECK("수표");

    private final String displayName;

    PaymentMethod(String displayName){
        this.displayName = displayName;
    }
}
