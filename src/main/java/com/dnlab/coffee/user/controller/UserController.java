package com.dnlab.coffee.user.controller;

import com.dnlab.coffee.user.entity.User;
import com.dnlab.coffee.user.common.UserJoinRequest;
import com.dnlab.coffee.user.common.UserLoginRequest;
import com.dnlab.coffee.user.common.UserUpdateRequest;
import com.dnlab.coffee.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/join")
    public String joinForm(){return "user/userJoinForm";}

    @PostMapping("/join")
    public String join(UserJoinRequest req, Model model){
        User user = userService.join(req);
        if(user == null) return "user/userJoinForm";
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/userLoginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginRequest req, HttpServletRequest httpServletRequest, Model model) {
        User user = userService.login(req);
        if (user == null) {
            log.info("유저가 없음");
            return "user/userLoginForm";
        }
        if(!user.isActive()){
            return "user/userLoginForm";
//            redirectAttributes.addAttribute("userId", user.getId());
//            return "redirect:/user/withdrawalUser";
        }

        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("loginUser", user);
        session.setMaxInactiveInterval(3600);
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/info")
    public String myInfo(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserById(loginUser.getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "user/myInfo";
    }

    @PostMapping("/update")
    public String update(HttpSession httpSession, @ModelAttribute UserUpdateRequest req, @SessionAttribute(name = "loginUser", required = false) User loginUser) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        User loginedUser = userService.update(loginUser.getId(), req);
        if (loginedUser == null) return "redirect:/user/info";   //추후 오류메시지 표시를 위한 예외처리
        httpSession.setAttribute("loginUser", loginedUser);
        return "redirect:/user/info";
    }

    @GetMapping("/withdrawal")
    public String withdrawal(@RequestParam("userId") Long userId, @SessionAttribute(name = "loginUser", required = false) User loginUser, HttpServletRequest request){
        if(loginUser==null)return "redirect:/user/login";
        User user = userService.withdrawal(userId);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
