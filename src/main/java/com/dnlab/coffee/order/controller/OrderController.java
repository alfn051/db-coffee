package com.dnlab.coffee.order.controller;

import com.dnlab.coffee.order.common.OrderMenuRequest;
import com.dnlab.coffee.order.common.PaymentMethod;
import com.dnlab.coffee.order.entity.Order;
import com.dnlab.coffee.order.entity.OrderMenu;
import com.dnlab.coffee.order.service.OrderService;
import com.dnlab.coffee.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/list")
    public String orderList(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model){
        if(loginUser==null)return "redirect:/user/login";
        List<Order> orderList = orderService.getAllOrders(loginUser.getId());
        model.addAttribute("orderList", orderList);
        model.addAttribute("loginUser", loginUser);
        return "order/list";
    }

    @PostMapping("/add")
    public String addCart(@SessionAttribute(name = "loginUser", required = false) User loginUser, @ModelAttribute OrderMenuRequest req, HttpServletRequest httpServletRequest){
        if(loginUser==null)return "redirect:/user/login";
        orderService.addToOrder(req.setUserId(loginUser.getId()));
        return "redirect:"+httpServletRequest.getHeader("Referer");
    }

    @GetMapping("/orderMenu")
    public String orderMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model){
        if(loginUser==null)return "redirect:/user/login";
        orderService.cancelQuickOrder(loginUser.getId());
        List<OrderMenu> orderMenuList = orderService.getPrepairingOrder(loginUser.getId()).getOrderMenus();
        long totalAmount = 0L;
        for(OrderMenu orderMenu : orderMenuList){
            totalAmount += (long) orderMenu.getQuantity() * orderMenu.getMenu().getPrice();
        }
        model.addAttribute("orderMenuList", orderMenuList);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("totalAmount", totalAmount);
        return "order/orderMenu";
    }

    @PostMapping("/update")
    public String changeQuantity(@SessionAttribute(name = "loginUser", required = false) User loginUser,  long orderMenuId, int quantity){
        if(loginUser==null)return "redirect:/user/login";
        orderService.changeQuantity(orderMenuId, quantity);
        return "redirect:/order/orderMenu";
    }

    @PostMapping("/delete")
    public String deleteCart(@SessionAttribute(name = "loginUser", required = false) User loginUser, long orderMenuId){
        if(loginUser==null)return "redirect:/user/login";
        orderService.removeOrderMenu(orderMenuId);
        return "redirect:/order/orderMenu";
    }

    @GetMapping("/confirm")
    public String confirmOrder(@SessionAttribute(name = "loginUser", required = false) User loginUser, PaymentMethod paymentMethod){
        if (loginUser == null) return "redirect:/user/login";
        orderService.confirmOrder(loginUser.getId(), paymentMethod);
        return "redirect:/";
    }

    @PostMapping("/quickOrder")
    public String quickOrder(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                             @ModelAttribute OrderMenuRequest req,
                             HttpServletRequest httpServletRequest,
                             Model model){
        if(loginUser==null)return "redirect:/user/login";
        orderService.cancelQuickOrder(loginUser.getId());
        OrderMenu orderMenu = orderService.quickOrder(req.setUserId(loginUser.getId()));
        long totalAmount = orderMenu.getQuantity() * orderMenu.getMenu().getPrice();
        model.addAttribute("orderMenu", orderMenu);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("loginUser", loginUser);
        return "order/quickOrder";
    }

    @GetMapping("/confirmQuick")
    public String confirmQuickOrder(@SessionAttribute(name = "loginUser", required = false) User loginUser, PaymentMethod paymentMethod){
        if (loginUser == null) return "redirect:/user/login";
        orderService.confirmQuickOrder(loginUser.getId(), paymentMethod);
        return "redirect:/";
    }

}
