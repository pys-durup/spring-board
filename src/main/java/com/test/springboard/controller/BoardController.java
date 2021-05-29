package com.test.springboard.controller;

import com.test.springboard.model.BoardDto;
import com.test.springboard.model.PageDto;
import com.test.springboard.repository.BoardRepository;
import com.test.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    // 글 목록 - 메인화면
    @GetMapping
    public String list(HttpServletRequest request,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword,
                       @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                       @PageableDefault(size = 4, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        // 임시 세션처리
        HttpSession session = request.getSession();
        session.setAttribute("writer", "admin");
        List<BoardDto> boardList;

        // 검색어 없을때
        if (keyword.equals("")){
            boardList = boardService.getBoardlist(pageNum, pageable);
        } else { // 검색어 있을때
            boardList = boardService.searchPosts(keyword, pageable);
        }

        PageDto pageDto = boardService.getPageList(pageNum, keyword, pageable);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageDto", pageDto);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("keyword", keyword);

        return "board/main";
    }

    // 글 목록 - 검색결과
//    @GetMapping("/search")
//    public String search(@RequestParam(value = "keyword") String keyword,
//                         @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
//                         @PageableDefault(size = 4, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
//                         Model model) {
//
//        List<BoardDto> boardDtoList = boardService.searchPosts(keyword, pageable);
//        Integer[] pageList = boardService.getPageList(pageNum);
//        PageDto pageDto = boardService.getBlockNum(pageNum);
//
//        model.addAttribute("pageList", pageList);
//        model.addAttribute("pageDto", pageDto);
//        model.addAttribute("boardList", boardDtoList);
//        model.addAttribute("keyword",keyword);
//
//        return "board/main";
//    }

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
        boardDto.setWriter((String) session.getAttribute("writer"));
        boardService.savePost(boardDto);

        return "redirect:/boards";
    }

    // 글 상세정보 보기
    @GetMapping("/{id}")
    public String view(@PathVariable("id") Long id,
                       @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                       Model model) {
        BoardDto boardDto = boardService.getPostOne(id);

        model.addAttribute("boardDto", boardDto);
        model.addAttribute("pageNum", pageNum);
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
        boardDto.setWriter((String) session.getAttribute("writer"));
        boardService.savePost(boardDto);

        // redirectAttributes 로 redirect 경로 처리
        redirectAttributes.addAttribute("id", id);
        return "redirect:/boards/{id}";
    }

    // 글 삭제 로직
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        boardService.deletePost(id);

        return "redirect:/boards";
    }
}
