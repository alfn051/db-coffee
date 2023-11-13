package com.dnlab.coffee.order.entity;

import com.dnlab.coffee.order.common.OrderState;
import com.dnlab.coffee.order.common.PaymentMethod;
import com.dnlab.coffee.user.entity.User;
import com.dnlab.coffee.config.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private Date orderedTime;

    private long totalAmount;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderMenu> orderMenus = new LinkedList<>();

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private PaymentMethod paymentMethod;

}