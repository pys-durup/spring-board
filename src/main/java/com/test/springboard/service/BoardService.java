package com.test.springboard.service;

import com.test.springboard.model.BoardDto;
import com.test.springboard.model.PageDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
//    List<BoardDto> searchPosts(String keyword);
    List<BoardDto> searchPosts(String keyword, Pageable pageable);
    long savePost(BoardDto boardDto);
    List getBoardlist(Integer pageNum);
    BoardDto getPostOne(Long id);
    void deletePost(Long id);
    public Long getBoardCount();
    public Integer[] getPageList(Integer curPageNum);
    PageDto getBlockNum(Integer pageNum);
}
