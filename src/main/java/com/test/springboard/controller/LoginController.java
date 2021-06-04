package com.test.springboard.controller;

import com.test.springboard.model.MemberDTO;
import com.test.springboard.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    // 회원가입 폼
    @GetMapping("/signUp")
    public String signUpForm() {
        return "login/signUp";
    }

    
    // 회원가입 로직
    @PostMapping("/signUp")
    public String signUp(@ModelAttribute MemberDTO memberdto) {

        loginService.signUp(memberdto);



        return "redirect:/login";
    }
    
    // 아이디 중복 체크 로직
    @ResponseBody
    @GetMapping("/signUp/{id}")
    public String checkDuplicate(@PathVariable String id) {
        System.out.println("id = " + id);

        return id;
    }
}
