package com.test.springboard.controller;

import com.test.springboard.model.BoardDto;
import com.test.springboard.repository.BoardRepository;
import com.test.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/boards")
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public String list(HttpServletRequest request,
                       Model model) {
        HttpSession session = request.getSession();
        session.setAttribute("writer", "admin");

        List boardList = boardService.getBoardlist();
        model.addAttribute("boardList", boardList);

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

    // 글 상세정보 보기
    @GetMapping("/{id}")
    public String view(@PathVariable("id") Long id,
                       Model model) {
        BoardDto boardDto = boardService.getPostOne(id);

        model.addAttribute("boardDto", boardDto);
        return "board/view";
    }

    @GetMapping("/edit")
    public String edit() {
        return "board/edit";
    }
}
