package com.dnlab.coffee.order.entity;

import com.dnlab.coffee.menu.entity.Menu;
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
@Table(name = "order_menu")
@Builder
public class OrderMenu extends BaseEntity {
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}