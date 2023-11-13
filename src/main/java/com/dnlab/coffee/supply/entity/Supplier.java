package com.dnlab.coffee.supply.entity;

import com.dnlab.coffee.common.AddressDTO;
import com.dnlab.coffee.config.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "supplier")
@Builder
public class Supplier extends BaseEntity {
    @Column
    private String name;
    @Column
    private String baseAddress;
    @Column
    private String detailAddress;
    @Column
    private String postalCode;
    @Column
    private boolean active;

    @OneToMany(mappedBy = "supplier", orphanRemoval = true)
    private List<Supply> supplies = new LinkedList<>();

    private AddressDTO getAddress(){
        return AddressDTO.builder()
                .baseAddress(baseAddress)
                .detailAddress(detailAddress)
                .postalCode(postalCode)
                .build();
    }
}