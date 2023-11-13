package com.dnlab.coffee.order.repository;

import com.dnlab.coffee.order.common.OrderState;
import com.dnlab.coffee.order.entity.Order;
import com.dnlab.coffee.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Optional<Order> findOneByUserAndOrderState(User user, OrderState orderState);
    public List<Order> findByUserAndOrderState(User user, OrderState orderState);
}