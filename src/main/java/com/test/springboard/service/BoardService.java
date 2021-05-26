package com.test.springboard.service;

import com.test.springboard.model.BoardDto;

public interface BoardService {
    long savePost(BoardDto boardDto);
}
