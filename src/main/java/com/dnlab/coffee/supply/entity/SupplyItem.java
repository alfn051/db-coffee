package com.dnlab.coffee.supply.entity;

import com.dnlab.coffee.menu.entity.Ingredient;
import com.dnlab.coffee.config.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "supply_list")
@Builder
public class SupplyItem extends BaseEntity {
    private long amount;
    private long price;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply;

}