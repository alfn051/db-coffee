package com.dnlab.coffee.menu.entity;

import com.dnlab.coffee.menu.common.StockUnit;
import com.dnlab.coffee.config.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ingredient")
public class Ingredient extends BaseEntity {
    @Column
    private String name;
    @Column
    private long stock;
    @Column
    private boolean active;
    @Enumerated(EnumType.STRING)
    @Column(name = "stock_unit")
    private StockUnit stockUnit;
}