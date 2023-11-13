package com.dnlab.coffee.common;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class AddressDTO {
    private String baseAddress;
    private String detailAddress;
    private String postalCode;
}
