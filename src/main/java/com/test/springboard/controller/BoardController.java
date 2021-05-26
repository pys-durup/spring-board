package com.test.springboard.controller;

import com.test.springboard.model.BoardDto;
import com.test.springboard.repository.BoardRepository;
import com.test.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // 글 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id,
                           Model model) {
        BoardDto boardDto = boardService.getPostOne(id);

        model.addAttribute("boardDto", boardDto);
        return "board/edit";
    }


    // 글 수정 로직
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id,
                       HttpServletRequest request,
                       RedirectAttributes redirectAttributes,
                       @ModelAttribute BoardDto boardDto) {
        // 임시로 작성자 정보 생성후 설정
        HttpSession session = request.getSession();
        boardDto.setWriter((String)session.getAttribute("writer"));
        boardService.savePost(boardDto);

        // redirectAttributes 로 redirect 경로 처리
        redirectAttributes.addAttribute("id", id);
        return "redirect:/boards/{id}";
    }
}
