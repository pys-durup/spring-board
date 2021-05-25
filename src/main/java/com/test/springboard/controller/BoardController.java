package com.test.springboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping
    public String list() {
        return "board/main";
    }

    @GetMapping("/write")
    public String write() {
        return "board/write";
    }

    @GetMapping("/view")
    public String view() {
        return "board/view";
    }

    @GetMapping("/edit")
    public String edit() {
        return "board/edit";
    }
}
