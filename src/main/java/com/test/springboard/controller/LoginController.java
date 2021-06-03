package com.test.springboard.controller;

import com.test.springboard.model.MemberDTO;
import com.test.springboard.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String login() {



        return "login/login";
    }

    @GetMapping("/signUp")
    public String signUpForm() {
        return "login/signUp";
    }

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute MemberDTO memberdto) {

        loginService.signUp(memberdto);



        return "redirect:/login";
    }
}
