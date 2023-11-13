package com.dnlab.coffee.supply.common;

import com.dnlab.coffee.supply.entity.Supplier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierDto {
    private String name;
    private String baseAddress;
    private String detailAddress;
    private String postalCode;

    public Supplier toEntity(){
        return Supplier.builder()
                .name(name)
                .baseAddress(baseAddress)
                .detailAddress(detailAddress)
                .postalCode(postalCode)
                .active(true)
                .build();
    }
}
