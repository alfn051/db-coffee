package com.dnlab.coffee.supply.entity;

import com.dnlab.coffee.config.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "supply")
@Builder
public class Supply extends BaseEntity {
    private LocalDateTime suppliedDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "supply", orphanRemoval = true)
    private List<SupplyItem> supplyItemList = new LinkedList<>();

    private boolean confirmed;
}