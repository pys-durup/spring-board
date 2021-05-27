package com.test.springboard.service;

import com.test.springboard.model.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> searchPosts(String keyword);
    long savePost(BoardDto boardDto);
    List getBoardlist();
    BoardDto getPostOne(Long id);
    void deletePost(Long id);
}
