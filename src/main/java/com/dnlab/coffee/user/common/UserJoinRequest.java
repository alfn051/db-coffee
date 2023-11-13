package com.dnlab.coffee.user.common;

import com.dnlab.coffee.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserJoinRequest {
    private String username;
    private String password;
    private String name;
    private Long phone;
    private String baseAddress;
    private String detailAddress;
    private String postalCode;

    public User toEntity(){
        return User.builder()
                .username(username)
                .password(password)
                .name(name)
                .phone(phone)
                .baseAddress(baseAddress)
                .detailAddress(detailAddress)
                .postalCode(postalCode)
                .active(true)
                .admin(false)
                .build();
    }
}
