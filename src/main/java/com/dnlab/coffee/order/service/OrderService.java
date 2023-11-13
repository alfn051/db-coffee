package com.dnlab.coffee.order.service;

import com.dnlab.coffee.menu.entity.Menu;
import com.dnlab.coffee.menu.service.MenuService;
import com.dnlab.coffee.order.common.OrderMenuRequest;
import com.dnlab.coffee.order.common.OrderState;
import com.dnlab.coffee.order.common.PaymentMethod;
import com.dnlab.coffee.order.entity.Order;
import com.dnlab.coffee.order.entity.OrderMenu;
import com.dnlab.coffee.order.repository.OrderMenuRepository;
import com.dnlab.coffee.order.repository.OrderRepository;
import com.dnlab.coffee.user.entity.User;
import com.dnlab.coffee.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final MenuService menuService;
    private final OrderMenuRepository orderMenuRepository;

    @Transactional
    public OrderMenu addToOrder(OrderMenuRequest req) {
        User user = userService.getUserById(req.getUserId());
        if (user == null) return null;
        Menu menu = menuService.getMenuById(req.getMenuId());
        if (menu == null) return null;
        Optional<Order> optionalOrder = orderRepository.findOneByUserAndOrderState(user, OrderState.PREPARING);
        Order order = optionalOrder.orElseGet(() -> createOrder(user, OrderState.PREPARING));
        for(OrderMenu orderMenu : order.getOrderMenus()){
            if(orderMenu.getMenu().equals(menu)){
                orderMenu.setQuantity(orderMenu.getQuantity()+ req.getQuantity());
                return null;
            }
        }
        OrderMenu orderMenu = OrderMenu.builder()
                .menu(menu)
                .order(order)
                .quantity(req.getQuantity())
                .build();
        return orderMenuRepository.save(orderMenu);
    }

    @Transactional
    public Order createPrepairingOrder(long userId){
        User user = userService.getUserById(userId);
        if (user == null) return null;
        Optional<Order> optionalOrder = orderRepository.findOneByUserAndOrderState(user, OrderState.PREPARING);
        return optionalOrder.orElseGet(() -> createOrder(user, OrderState.PREPARING));
    }

    public void removeOrderMenu(long orderMenuId){
        Optional<OrderMenu> optionalOrderMenu = orderMenuRepository.findById(orderMenuId);
        if(optionalOrderMenu.isEmpty()) return;
        OrderMenu orderMenu = optionalOrderMenu.get();
        orderMenuRepository.delete(orderMenu);
    }

    public OrderMenu changeQuantity(Long orderMenuId, Integer quantity){
        Optional<OrderMenu> optionalOrderMenu = orderMenuRepository.findById(orderMenuId);
        if(optionalOrderMenu.isEmpty()) return null;
        if(quantity<=0){
            removeOrderMenu(orderMenuId);
            return null;
        }
        OrderMenu orderMenu = optionalOrderMenu.get();
        orderMenu.setQuantity(quantity);
        return orderMenuRepository.save(orderMenu);
    }

    public Order getPrepairingOrder(long userId){
        User user = userService.getUserById(userId);
        if (user == null) return null;
        return orderRepository.findOneByUserAndOrderState(user, OrderState.PREPARING).orElse(null);
    }

    @Transactional
    public OrderMenu quickOrder(OrderMenuRequest req) {
        User user = userService.getUserById(req.getUserId());
        if (user == null) return null;
        Menu menu = menuService.getMenuById(req.getMenuId());
        if (menu == null) return null;
        Order order = createOrder(user, OrderState.QUICK);
        OrderMenu orderMenu = OrderMenu.builder()
                .menu(menu)
                .order(order)
                .quantity(req.getQuantity())
                .build();
        return orderMenuRepository.save(orderMenu);
    }

    @Transactional
    public void cancelQuickOrder(long userId){
        User user = userService.getUserById(userId);
        Optional<Order> optionalOrder = orderRepository.findOneByUserAndOrderState(user, OrderState.QUICK);
        if(optionalOrder.isEmpty()) return;
        Order order = optionalOrder.get();
        orderRepository.delete(order);
    }

    public Order createOrder(User user, OrderState orderState) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderState(orderState);
        return orderRepository.save(order);
    }

    @Transactional
    public Order confirmOrder(long userId, PaymentMethod paymentMethod) {
        User user = userService.getUserById(userId);
        if(user == null) return null;
        Optional<Order> optionalOrder = orderRepository.findOneByUserAndOrderState(user, OrderState.PREPARING);
        return processConfirmOrder(paymentMethod, optionalOrder);
    }

    @Transactional
    public Order confirmQuickOrder(long userId, PaymentMethod paymentMethod) {
        User user = userService.getUserById(userId);
        if(user == null) return null;
        Optional<Order> optionalOrder = orderRepository.findOneByUserAndOrderState(user, OrderState.QUICK);
        return processConfirmOrder(paymentMethod, optionalOrder);
    }

    private Order processConfirmOrder(PaymentMethod paymentMethod, Optional<Order> optionalOrder) {
        if (optionalOrder.isEmpty()) return null;
        Order order = optionalOrder.get();
        long totalAmount = 0L;
        for (OrderMenu orderMenu : order.getOrderMenus()) {
            totalAmount += orderMenu.getQuantity()*orderMenu.getMenu().getPrice();
            if (!menuService.checkStockAvailable(orderMenu)) return null;
        }
        order.getOrderMenus().forEach(menuService::subIngredientStock);

        order.setTotalAmount(totalAmount);
        order.setPaymentMethod(paymentMethod);
        order.setOrderedTime(new Date());
        order.setOrderState(OrderState.ORDERED);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(long userId){
        User user = userService.getUserById(userId);
        if(user == null) return null;
        return orderRepository.findByUserAndOrderState(user, OrderState.ORDERED);
    }

}
