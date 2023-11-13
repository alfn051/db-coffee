package com.dnlab.coffee.user.common;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String password;
    private String name;
    private Long phone;
    private String baseAddress;
    private String detailAddress;
    private String postalCode;
}
