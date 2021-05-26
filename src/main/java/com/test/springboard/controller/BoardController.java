package com.test.springboard.controller;

import com.test.springboard.model.BoardDto;
import com.test.springboard.repository.BoardRepository;
import com.test.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/boards")
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public String list(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("writer", "admin");
        return "board/main";
    }

    // 글쓰기 폼
    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }

    // 글쓰기 로직
    @PostMapping("/write")
    public String write(@ModelAttribute BoardDto boardDto,
                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        boardDto.setWriter((String)session.getAttribute("writer"));
        boardService.savePost(boardDto);

        return "redirect:/boards";
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
