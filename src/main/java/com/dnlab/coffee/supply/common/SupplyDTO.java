package com.dnlab.coffee.supply.common;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SupplyDTO {
    private long supplierId;
    private List<SupplyItemDTO> supplyItemDTOList;
    private LocalDateTime suppliedDate;
}
