package com.dnlab.coffee.menu.controller;

import com.dnlab.coffee.menu.common.IngredientDTO;
import com.dnlab.coffee.menu.common.MenuDTO;
import com.dnlab.coffee.menu.common.RecipeDTO;
import com.dnlab.coffee.menu.entity.Ingredient;
import com.dnlab.coffee.menu.entity.Menu;
import com.dnlab.coffee.menu.entity.Recipe;
import com.dnlab.coffee.menu.service.MenuService;
import com.dnlab.coffee.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public String menuInfo(@RequestParam("menuId") Long menuId, @SessionAttribute(name = "loginUser", required = false) User loginUser, Model model){
        Menu menu = menuService.getMenuById(menuId);
        if(loginUser!=null)model.addAttribute(loginUser);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("menu", menu);
        return "menu/info";
    }

    @GetMapping("/manage")
    public String manage(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        model.addAttribute("loginUser", loginUser);

        List<Menu> menuList = menuService.getAllMenus();
        model.addAttribute("menuList", menuList);

        List<Ingredient> ingredientList = menuService.getAllIngredients();
        model.addAttribute("ingredientList", ingredientList);

        return "menu/manage";
    }

    @PostMapping("/add")
    public String createMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, MenuDTO dto){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.createMenu(dto);
        return "redirect:/menu/manage";
    }

    @PostMapping("/update")
    public String updateMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, MenuDTO dto, long menuId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.updateMenu(menuId, dto);
        return "redirect:/menu/manage";
    }

    @PostMapping("/remove")
    public String removeMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, long menuId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.removeMenu(menuId);
        return "redirect:/menu/manage";
    }

    @PostMapping("/ingredient/add")
    public String createIngredient(@SessionAttribute(name = "loginUser", required = false) User loginUser, IngredientDTO dto){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.createIngredient(dto);
        return "redirect:/menu/manage";
    }

    @PostMapping("/ingredient/update")
    public String updateIngredient(@SessionAttribute(name = "loginUser", required = false) User loginUser, IngredientDTO dto, long ingredientId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.updateIngredient(ingredientId, dto);
        return "redirect:/menu/manage";
    }

    @PostMapping("/ingredient/remove")
    public String removeIngredient(@SessionAttribute(name = "loginUser", required = false) User loginUser, long ingredientId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.removeIngredient(ingredientId);
        return "redirect:/menu/manage";
    }

    @GetMapping("/recipe")
    public String recipe(@RequestParam("menuId") Long menuId, @SessionAttribute(name = "loginUser", required = false) User loginUser, Model model){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        model.addAttribute("loginUser", loginUser);

        Menu menu = menuService.getMenuById(menuId);
        if(menu==null) return "redirect:/menu/manage";

        List<Ingredient> ingredientList = menuService.getAllIngredients();
        model.addAttribute("ingredientList", ingredientList);

        model.addAttribute("menu", menu);

        return "menu/recipe";
    }

    @PostMapping("/recipe/add")
    public String createRecipe(@SessionAttribute(name = "loginUser", required = false) User loginUser, RecipeDTO dto, HttpServletRequest httpServletRequest){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.createRecipe(dto);
        return "redirect:"+httpServletRequest.getHeader("Referer");
    }

    @PostMapping("/recipe/update")
    public String updateRecipe(@SessionAttribute(name = "loginUser", required = false) User loginUser, long recipeId, long requiredAmount, HttpServletRequest httpServletRequest){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.updateRecipe(recipeId, requiredAmount);
        return "redirect:"+httpServletRequest.getHeader("Referer");
    }

    @PostMapping("/recipe/remove")
    public String removeRecipe(@SessionAttribute(name = "loginUser", required = false) User loginUser, long recipeId, HttpServletRequest httpServletRequest){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.removeRecipe(recipeId);
        return "redirect:"+httpServletRequest.getHeader("Referer");
    }
}
