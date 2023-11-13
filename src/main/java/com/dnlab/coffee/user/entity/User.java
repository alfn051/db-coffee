package com.dnlab.coffee.user.entity;

import com.dnlab.coffee.common.AddressDTO;
import com.dnlab.coffee.config.BaseEntity;
import com.dnlab.coffee.order.entity.Order;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user")
public class User extends BaseEntity {
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private long phone;
    @Column
    private String baseAddress;
    @Column
    private String detailAddress;
    @Column
    private String postalCode;
    @Column
    private boolean active;
    @Column
    private boolean admin;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    private AddressDTO getAddress(){
        return AddressDTO.builder()
                .baseAddress(baseAddress)
                .detailAddress(detailAddress)
                .postalCode(postalCode)
                .build();
    }
}