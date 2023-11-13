package com.dnlab.coffee.order.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderMenuRequest {
    private long menuId;
    private int quantity;
    private Long userId;

    public OrderMenuRequest setUserId(long userId){
        this.userId = userId;
        return this;
    }
}
