package com.dnlab.coffee.supply.common;

import lombok.Data;

@Data
public class SupplyItemDTO {
    private long amount;
    private long price;
    private long ingredientId;
}
