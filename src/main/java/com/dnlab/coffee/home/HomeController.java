package com.dnlab.coffee.home;

import com.dnlab.coffee.menu.common.Category;
import com.dnlab.coffee.menu.entity.Menu;
import com.dnlab.coffee.menu.service.MenuService;
import com.dnlab.coffee.order.common.OrderState;
import com.dnlab.coffee.order.entity.Order;
import com.dnlab.coffee.order.repository.OrderRepository;
import com.dnlab.coffee.order.service.OrderService;
import com.dnlab.coffee.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final MenuService menuService;
    private final OrderService orderService;

    @GetMapping
    public String home(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                       @RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "category", required = false)Category category,
                       Model model,
                       HttpSession session){
        if(loginUser != null){
            model.addAttribute("loginUser", loginUser);
            orderService.createPrepairingOrder(loginUser.getId());
        }

        List<Menu> menuList = menuService.getAllSearchedMenu(search, category);
        model.addAttribute("menuList", menuList);
        model.addAttribute("search", search);
        model.addAttribute("category", category);
        return "home";
    }

//    @GetMapping("search")
//    public String searchedHome(@RequestParam(value = "search") String search,
//                               @SessionAttribute(name = "loginUser", required = false) User loginUser,
//                               Model model){
//        if(loginUser != null) model.addAttribute("loginUser", loginUser);
//
//        List<Menu> menuList= menuService.getAllSearchedMenu(search);
//        model.addAttribute("menuList", menuList);
//        model.addAttribute("search", search);
//        return "home";
//    }
}
